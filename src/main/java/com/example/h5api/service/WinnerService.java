package com.example.h5api.service;

import com.example.h5api.dto.WinnerDto;
import com.example.h5api.dto.WinnerDtoWithoutDates;
import com.example.h5api.entity.Winner;
import com.example.h5api.exceptions.GenericEmptyListException;
import com.example.h5api.exceptions.GenericNotFoundException;
import com.example.h5api.repository.WinnerRepository;
import com.example.h5api.utils.WinnerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WinnerService implements GenericService<WinnerDto> {
    private final WinnerRepository winnerRepository;

    private final WinnerUtil winnerUtil;

    @Autowired
    public WinnerService(WinnerRepository winnerRepository, WinnerUtil winnerUtil) {
        this.winnerRepository = winnerRepository;
        this.winnerUtil = winnerUtil;
    }

    @Override
    @Transactional(readOnly = true)
    public List<WinnerDto> findAll() {
        List<Winner> winnerList = new ArrayList<>();
        winnerRepository.findAll().forEach(winnerList::add);
        if (winnerList.isEmpty()) {
            throw new GenericEmptyListException();
        }
        return winnerList.stream()
                .map(winnerUtil::transformFromWinnerToWinnerDto).collect(Collectors.toList());

    }

    @Override
    @Transactional(readOnly = true)
    public WinnerDto findById(Integer id) {
        Winner winner = winnerRepository.findById(id).orElseThrow(() -> new GenericNotFoundException(id));
        return winnerUtil.transformFromWinnerToWinnerDto(winner);
    }

    @Override
    @Transactional
    public WinnerDto save(WinnerDto winnerDto) {
        winnerRepository.save(winnerUtil.transformFromWinnerDtoToWinner(winnerDto));
        return winnerDto;
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        Winner winner = winnerRepository.findById(id).orElseThrow(() -> new GenericNotFoundException(id));
        if (winner != null) {
            winner.setDeleteAt(LocalDate.now());
            winnerRepository.save(winner);
        }
    }

    @Override
    @Transactional
    public Boolean existById(Integer id) {
        return winnerRepository.existsById(id);
    }

    @Transactional(readOnly = true)
    public List<WinnerDtoWithoutDates> findWinnerByCampaignId(Integer id) {
        List<Winner> winnerList = new ArrayList<>();
        winnerRepository.findWinnerByCampaignId(id).forEach(winnerList::add);
        if (winnerList.isEmpty()) {
            throw new GenericEmptyListException();
        }
        return winnerList.stream()
                .map(winnerUtil::transformFromWinnerToWinnerDtoWithoutDates).collect(Collectors.toList());
    }

    @Transactional
    public List<WinnerDtoWithoutDates> findWinnersFromLastCampaignWithoutDates() {
        if (winnerRepository.findLastCampaignId() == null) {
            throw new GenericNotFoundException(-404);
        }
        int lastCampaign = winnerRepository.findLastCampaignId();
        List<Winner> winnerList = new ArrayList<>();
        winnerRepository.findWinnerByCampaignId(lastCampaign).forEach(winnerList::add);
        if (winnerList.isEmpty()) {
            throw new GenericEmptyListException();
        }
        return winnerList.stream()
                .map(winnerUtil::transformFromWinnerToWinnerDtoWithoutDates).collect(Collectors.toList());
    }

    public List<WinnerDtoWithoutDates> findAllWithoutDates() {
        List<Winner> winnerList = new ArrayList<>();
        winnerRepository.findAll().forEach(winnerList::add);
        if (winnerList.isEmpty()) {
            throw new GenericEmptyListException();
        }
        return winnerList.stream()
                .map(winnerUtil::transformFromWinnerToWinnerDtoWithoutDates).collect(Collectors.toList());
    }
}
