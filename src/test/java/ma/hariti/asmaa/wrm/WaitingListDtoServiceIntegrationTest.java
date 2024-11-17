package ma.hariti.asmaa.wrm;

import jakarta.validation.ConstraintViolationException;
import ma.hariti.asmaa.wrm.dto.VisitDTO;
import ma.hariti.asmaa.wrm.dto.WaitingListDto;
import ma.hariti.asmaa.wrm.embeddedable.VisitId;
import ma.hariti.asmaa.wrm.entity.Visit;
import ma.hariti.asmaa.wrm.entity.Visitor;
import ma.hariti.asmaa.wrm.entity.WaitingList;
import ma.hariti.asmaa.wrm.enumeration.Status;
import ma.hariti.asmaa.wrm.mapper.VisitMapper;
import ma.hariti.asmaa.wrm.mapper.WaitingListMapper;
import ma.hariti.asmaa.wrm.repository.VisitorRepository;
import ma.hariti.asmaa.wrm.repository.WaitingListRepository;
import ma.hariti.asmaa.wrm.service.waitingList.WaitingListDtoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class WaitingListDtoServiceIntegrationTest {

    @Autowired
    private WaitingListDtoServiceImpl waitingListDtoService;

    @Autowired
    private WaitingListRepository waitingListRepository;

    @Autowired
    private VisitorRepository visitorRepository;

    @Autowired
    private WaitingListMapper waitingListMapper;

    @Autowired
    private VisitMapper visitMapper;

    private WaitingList testWaitingList;
    private WaitingListDto testWaitingListDto;
    private Visitor testVisitor;

    @BeforeEach
    void setUp() {
        // Create a valid visitor first
        testVisitor = new Visitor();
        testVisitor.setFirstName("John");
        testVisitor.setLastName("Doe");
        testVisitor.setVisits(new ArrayList<>());
        testVisitor = visitorRepository.save(testVisitor);

        // Create waiting list with future date
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        Date tomorrow = cal.getTime();

        testWaitingList = new WaitingList();
        testWaitingList.setId(1L);
        testWaitingList.setDate(tomorrow);
        testWaitingList.setAlgorithm("First In First Out");
        testWaitingList.setCapacity(10);
        testWaitingList.setMode("Standard");
        testWaitingList.setVisits(new ArrayList<>());

        // Save waiting list first
        testWaitingList = waitingListRepository.save(testWaitingList);

        // Create visits and save them directly
        Visit visit1 = createAndSaveVisit(LocalDateTime.now(), (byte) 2, Duration.ofMinutes(45));
        Visit visit2 = createAndSaveVisit(LocalDateTime.now().plusHours(1), (byte) 1, Duration.ofMinutes(30));

        // Update references
        testWaitingList.getVisits().add(visit1);
        testWaitingList.getVisits().add(visit2);

        // Convert to DTO
        testWaitingListDto = waitingListMapper.toDto(testWaitingList);
    }

    private Visit createAndSaveVisit(LocalDateTime arrivalTime, byte priority, Duration processingTime) {
        Visit visit = new Visit();

        // Create and set VisitId
        VisitId id = new VisitId(testVisitor.getId(), testWaitingList.getId());
        visit.setId(id);

        // Set required fields
        visit.setArrivalTime(arrivalTime);
        visit.setStartTime(LocalTime.of(9, 0));
        visit.setEndTime(LocalTime.of(17, 0));
        visit.setStatus(Status.PENDING);
        visit.setPriority(priority);
        visit.setEstimatedProcessingTime(processingTime);

        // Set relationships
        visit.setVisitor(testVisitor);
        visit.setWaitingList(testWaitingList);

        // Add to visitor's visits
        if (!testVisitor.getVisits().contains(visit)) {
            testVisitor.getVisits().add(visit);
        }

        return visit;
    }

    @Test
    void testCreateWaitingList_Success() {
        WaitingListDto newDto = new WaitingListDto();
        newDto.setId(2L);
        newDto.setDate(getTomorrowDate());
        newDto.setAlgorithm("Priority");
        newDto.setCapacity(5);
        newDto.setMode("Standard");

        WaitingListDto savedDto = waitingListDtoService.create(newDto);

        assertNotNull(savedDto.getId());
        assertEquals("Priority", savedDto.getAlgorithm());
        assertEquals(5, savedDto.getCapacity());
    }
    @ParameterizedTest
    @ValueSource(strings = {"First In First Out", "Priority", "Shortest Job First"})
    void testCreateWaitingList_ValidAlgorithms(String algorithm) {
        WaitingListDto newDto = new WaitingListDto();
        newDto.setId(3L); // Set unique ID for each test
        newDto.setDate(getTomorrowDate());
        newDto.setAlgorithm(algorithm);
        newDto.setCapacity(5);
        newDto.setMode("Standard");

        WaitingListDto savedDto = waitingListDtoService.create(newDto);
        assertEquals(algorithm, savedDto.getAlgorithm());
    }

    @Test
    void testFindById_ExistingWaitingList() {
        Optional<WaitingListDto> foundDto = waitingListDtoService.findById(testWaitingList.getId());

        assertTrue(foundDto.isPresent());
        assertEquals(testWaitingList.getId(), foundDto.get().getId());
        assertEquals(testWaitingList.getAlgorithm(), foundDto.get().getAlgorithm());
        assertEquals(testWaitingList.getCapacity(), foundDto.get().getCapacity());
    }

    @Test
    void testFindById_NonExistingWaitingList() {
        Optional<WaitingListDto> foundDto = waitingListDtoService.findById(999L);
        assertTrue(foundDto.isEmpty());
    }

    @Test
    void testUpdateWaitingList_Success() {
        testWaitingListDto.setCapacity(15);
        testWaitingListDto.setMode("Express");
        testWaitingListDto.setAlgorithm("Priority");

        WaitingListDto updatedDto = waitingListDtoService.update(testWaitingListDto.getId(), testWaitingListDto);

        assertEquals(15, updatedDto.getCapacity());
        assertEquals("Express", updatedDto.getMode());
        assertEquals("Priority", updatedDto.getAlgorithm());

        WaitingList updatedEntity = waitingListRepository.findById(testWaitingList.getId()).orElseThrow();
        assertEquals(15, updatedEntity.getCapacity());
        assertEquals("Express", updatedEntity.getMode());
    }

    @Test
    void testScheduleVisits_FirstInFirstOut() {
        testWaitingList.setAlgorithm("First In First Out");
        waitingListRepository.save(testWaitingList);

        List<VisitDTO> scheduledVisits = waitingListDtoService.scheduleVisits(testWaitingList.getId());

        assertNotNull(scheduledVisits);
        assertEquals(2, scheduledVisits.size());
        assertTrue(scheduledVisits.get(0).getArrivalTime()
                .isBefore(scheduledVisits.get(1).getArrivalTime()));
    }

    @Test
    void testScheduleVisits_Priority() {
        testWaitingList.setAlgorithm("Priority");
        waitingListRepository.save(testWaitingList);

        List<VisitDTO> scheduledVisits = waitingListDtoService.scheduleVisits(testWaitingList.getId());

        assertNotNull(scheduledVisits);
        assertEquals(2, scheduledVisits.size());
        assertTrue(scheduledVisits.get(0).getPriority() >= scheduledVisits.get(1).getPriority());
    }

    @Test
    void testScheduleVisits_ShortestJobFirst() {
        testWaitingList.setAlgorithm("Shortest Job First");
        waitingListRepository.save(testWaitingList);

        List<VisitDTO> scheduledVisits = waitingListDtoService.scheduleVisits(testWaitingList.getId());

        assertNotNull(scheduledVisits);
        assertEquals(2, scheduledVisits.size());
        assertTrue(scheduledVisits.get(0).getEstimatedProcessingTime()
                .compareTo(scheduledVisits.get(1).getEstimatedProcessingTime()) <= 0);
    }

    @Test
    void testValidateCapacityConstraints() {
        WaitingListDto invalidDto = new WaitingListDto();
        invalidDto.setId(4L); // Set ID
        invalidDto.setDate(getTomorrowDate());
        invalidDto.setAlgorithm("First In First Out");
        invalidDto.setCapacity(0);
        invalidDto.setMode("Standard");

        assertThrows(ConstraintViolationException.class, () ->
                waitingListDtoService.create(invalidDto));
    }

    @Test
    void testValidateDateConstraints() {
        WaitingListDto invalidDto = new WaitingListDto();
        invalidDto.setId(5L);
        invalidDto.setDate(getYesterdayDate());
        invalidDto.setAlgorithm("First In First Out");
        invalidDto.setCapacity(5);
        invalidDto.setMode("Standard");

        assertThrows(ConstraintViolationException.class, () ->
                waitingListDtoService.create(invalidDto));
    }

    private Date getTomorrowDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    private Date getYesterdayDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }
}