package com.example.h5api.service;

import com.example.h5api.dto.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class NominationServiceTest {

    @Mock
    private NominationService nominationService;

    private NominationDto nominationDto;
    private NominationDto nominationDto2;
    private ValueDtoCountId valueDtoCountId;
    private ValueDtoIdName valueDtoIdName;
    private NominationDtoCounterRepeat nominationDtoCounterRepeat;
    private NominationDtoCounterValueIdUserId nominationDtoCounterValueIdUserId;
    private UserDtoIdName userDtoIdName;
    private NominationDtoAdmin nominationDtoAdmin;
    private NominationDtoWithoutDates nominationDtoWithoutDates;
    private List<NominationDtoCounterValueIdUserId> nominationDtoCounterValueIdUserIdList = new ArrayList<>();
    private List<NominationDto> nominationDtoList = new ArrayList<>();
    private List<ValueDtoCountId> valueDtoCountIdList = new ArrayList<>();
    private List<NominationDtoCounterRepeat> nominationDtoCounterRepeatList = new ArrayList<>();
    private Set<UserDtoIdName> userDtoIdNameSet = new HashSet<>();
    private List<UserDtoIdName> userDtoIdNameList = new ArrayList<>();
    private List<ValueDtoIdName> valueDtoIdNamesList = new ArrayList<>();
    private List<NominationDtoAdmin> nominationDtoAdminList = new ArrayList<>();
    private List<NominationDtoWithoutDates> nominationDtoWithoutDatesList = new ArrayList<>();

    @Before
    public void setup() {
        nominationDtoWithoutDates = new NominationDtoWithoutDates();
        valueDtoCountId = new ValueDtoCountId();
        nominationDto2 = new NominationDto();
        valueDtoIdName = new ValueDtoIdName();
        nominationDto = new NominationDto();
        userDtoIdName = new UserDtoIdName();
        nominationDtoAdmin = new NominationDtoAdmin();
        nominationDtoCounterRepeat = new NominationDtoCounterRepeat();
        nominationDtoCounterValueIdUserId = new NominationDtoCounterValueIdUserId();
        nominationDtoWithoutDates.setId(1);
        valueDtoIdName.setId(1);
        userDtoIdName.setId(1);
        userDtoIdName.setName("Sebastian");
        nominationDtoCounterValueIdUserId.setUserId(1);
        nominationDtoCounterRepeat.setRepeat(1);
        valueDtoCountId.setValueId(1);
        nominationDto.setId(1);
        nominationDto2.setId(2);
        nominationDtoAdmin.setValueId(1);
        nominationDtoAdmin.setValueName("Play");
        nominationDtoList.add(nominationDto);
        nominationDtoList.add(nominationDto2);
        valueDtoCountIdList.add(valueDtoCountId);
        nominationDtoCounterRepeatList.add(nominationDtoCounterRepeat);
        nominationDtoCounterValueIdUserIdList.add(nominationDtoCounterValueIdUserId);
        userDtoIdNameSet.add(userDtoIdName);
        userDtoIdNameList.add(userDtoIdName);
        valueDtoIdNamesList.add(valueDtoIdName);
        nominationDtoAdminList.add(nominationDtoAdmin);
        nominationDtoWithoutDatesList.add(nominationDtoWithoutDates);
    }

    @Test
    public void findAll() {
        Mockito.when(nominationService.findAll()).thenReturn(nominationDtoList);
        List<NominationDto> responseList = nominationService.findAll();
        assertNotNull(responseList);
        Assert.assertEquals(1, responseList.get(0).getId());
        verify(nominationService).findAll();
        verifyNoMoreInteractions(nominationService);
    }


    @Test
    public void findById() {
        Mockito.when(nominationService.findById(Mockito.anyInt())).thenReturn(nominationDto);
        NominationDto response = nominationService.findById(1);
        assertNotNull(response);
        Assert.assertEquals(1, response.getId());
        verify(nominationService).findById(Mockito.anyInt());
        verifyNoMoreInteractions(nominationService);
    }

    @Test
    public void save() {
        Mockito.when(nominationService.save(Mockito.any(NominationDto.class))).thenReturn(nominationDto);
        NominationDto response = nominationService.save(nominationDto);
        assertNotNull(response);
        Assert.assertEquals(1, response.getId());
        verify(nominationService).save(Mockito.any(NominationDto.class));
        verifyNoMoreInteractions(nominationService);
    }

    @Test
    public void deleteById() {
        nominationService.deleteById(nominationDto.getId());
        verify(nominationService).deleteById(Mockito.anyInt());
        verifyNoMoreInteractions(nominationService);
    }

    @Test
    public void existById() {
        Mockito.when(nominationService.existById(Mockito.anyInt())).thenReturn(true);
        Boolean response = nominationService.existById(1);
        assertNotNull(response);
        Assert.assertEquals(true, response);
        verify(nominationService).existById(Mockito.anyInt());
        verifyNoMoreInteractions(nominationService);
    }

    @Test
    public void nominationSummaryWithDate() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date myDate = sdf.parse("2020-01-01");
        Mockito.when(nominationService.nominationSummary(any(Date.class))).thenReturn(valueDtoCountIdList);
        List<ValueDtoCountId> response = nominationService.nominationSummary(myDate);
        assertNotNull(response);
        verify(nominationService).nominationSummary(Mockito.any(Date.class));
        verifyNoMoreInteractions(nominationService);
    }

    @Test
    public void testNominationSummary() {
        Mockito.when(nominationService.nominationSummary(any(Date.class))).thenReturn(valueDtoCountIdList);
        List<ValueDtoCountId> response = nominationService.nominationSummary(new Date());
        assertNotNull(response);
        verify(nominationService).nominationSummary(Mockito.any(Date.class));
        verifyNoMoreInteractions(nominationService);
    }

    @Test
    public void counterRepeats() {
        Mockito.when(nominationService.counterRepeats(any(Date.class))).thenReturn(nominationDtoCounterRepeatList);
        List<NominationDtoCounterRepeat> response = nominationService.counterRepeats(new Date());
        assertNotNull(response);
        verify(nominationService).counterRepeats(Mockito.any(Date.class));
        verifyNoMoreInteractions(nominationService);
    }

    @Test
    public void testCounterRepeatsWithDate() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date myDate = sdf.parse("2020-01-01");
        Mockito.when(nominationService.counterRepeats(any(Date.class))).thenReturn(nominationDtoCounterRepeatList);
        List<NominationDtoCounterRepeat> response = nominationService.counterRepeats(myDate);
        assertNotNull(response);
        verify(nominationService).counterRepeats(Mockito.any(Date.class));
        verifyNoMoreInteractions(nominationService);
    }

    @Test
    public void drawWinnersOfQuarter() {
        Mockito.when(nominationService.drawWinnersOfQuarter(any(Date.class))).thenReturn(nominationDtoCounterValueIdUserIdList);
        List<NominationDtoCounterValueIdUserId> response = nominationService.drawWinnersOfQuarter(new Date());
        assertNotNull(response);
        verify(nominationService).drawWinnersOfQuarter(Mockito.any(Date.class));
        verifyNoMoreInteractions(nominationService);
    }

    @Test
    public void testDrawWinnersOfQuarterWithDate() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date myDate = sdf.parse("2020-01-01");
        Mockito.when(nominationService.drawWinnersOfQuarter(any(Date.class))).thenReturn(nominationDtoCounterValueIdUserIdList);
        List<NominationDtoCounterValueIdUserId> response = nominationService.drawWinnersOfQuarter(myDate);
        assertNotNull(response);
        verify(nominationService).drawWinnersOfQuarter(Mockito.any(Date.class));
        verifyNoMoreInteractions(nominationService);
    }

    @Test
    public void findAllUserList() {
        Mockito.when(nominationService.findAllUserList(Mockito.anyInt())).thenReturn(userDtoIdNameSet);
        Set<UserDtoIdName> responseSet = nominationService.findAllUserList(1);
        assertNotNull(responseSet);
        Assert.assertEquals(1, responseSet.size());
        verify(nominationService).findAllUserList(Mockito.anyInt());
        verifyNoMoreInteractions(nominationService);
    }

    @Test
    public void findAllUserNominatorList() {
        Mockito.when(nominationService.findAllUserNominatorList(Mockito.anyInt(), Mockito.anyInt())).thenReturn(userDtoIdNameList);
        List<UserDtoIdName> responseList = nominationService.findAllUserNominatorList(1, 1);
        assertNotNull(responseList);
        Assert.assertEquals(1, responseList.size());
        Assert.assertEquals(1, responseList.get(0).getId());
        verify(nominationService).findAllUserNominatorList(Mockito.anyInt(), Mockito.anyInt());
        verifyNoMoreInteractions(nominationService);
    }

    @Test
    public void findAllValueList() {
        Mockito.when(nominationService.findAllValueList()).thenReturn(valueDtoIdNamesList);
        List<ValueDtoIdName> responseList = nominationService.findAllValueList();
        assertNotNull(responseList);
        Assert.assertEquals(1, responseList.size());
        Assert.assertEquals(1, responseList.get(0).getId());
        verify(nominationService).findAllValueList();
        verifyNoMoreInteractions(nominationService);
    }

    @Test
    public void showAdminNominations() {
        Mockito.when(nominationService.showAdminNominations()).thenReturn(nominationDtoAdminList);
        List<NominationDtoAdmin> responseList = nominationService.showAdminNominations();
        assertNotNull(responseList);
        Assert.assertEquals(1, responseList.size());
        Assert.assertEquals(1, responseList.get(0).getValueId());
        Assert.assertEquals("Play", responseList.get(0).getValueName());
        verify(nominationService).showAdminNominations();
        verifyNoMoreInteractions(nominationService);
    }

    @Test
    public void findAllWithoutDates() {
        Mockito.when(nominationService.findAllWithoutDates()).thenReturn(nominationDtoWithoutDatesList);
        List<NominationDtoWithoutDates> responseList = nominationService.findAllWithoutDates();
        assertNotNull(responseList);
        Assert.assertEquals(1, responseList.size());
        Assert.assertEquals(1, responseList.get(0).getId());
        verify(nominationService).findAllWithoutDates();
        verifyNoMoreInteractions(nominationService);
    }
}