package com.example.h5api.service;

import com.example.h5api.builders.Transformer;
import com.example.h5api.dao.IWinnerDao;
import com.example.h5api.dto.WinnerDto;
import com.example.h5api.dto.WinnerDtoWithoutDates;
import com.example.h5api.entity.Winner;
import com.example.h5api.exceptions.GenericEmptyListException;
import com.example.h5api.exceptions.GenericNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WinnerService extends Transformer implements IGenericService<WinnerDto> {
    @Autowired
    private IWinnerDao winnerDao;

    @Override
    @Transactional(readOnly = true)
    public List<WinnerDto> findAll() {
        List<Winner> winnerList = new ArrayList<>();
        winnerDao.findAll().forEach(winnerList::add);
        if (winnerList.isEmpty()) {
            throw new GenericEmptyListException();
        }
        return winnerList.stream()
                .map(this::transformFromWinnerToWinnerDto).collect(Collectors.toList());

    }

    @Override
    @Transactional(readOnly = true)
    public WinnerDto findById(Integer id) {
        Winner winner = winnerDao.findById(id).orElseThrow(()->new GenericNotFoundException(id));
        return transformFromWinnerToWinnerDto(winner);
    }

    @Override
    @Transactional
    public WinnerDto save(WinnerDto winnerDto) {
        winnerDao.save(transformFromWinnerDtoToWinner(winnerDto));
        return winnerDto;
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        Winner winner = winnerDao.findById(id).orElseThrow(()->new GenericNotFoundException(id));
        if (winner != null) {
            winner.setDeleteAt(new Date());
            winnerDao.save(winner);
        }
    }

    @Override
    @Transactional
    public Boolean existById(Integer id) {
        return winnerDao.existsById(id);
    }

    @Transactional(readOnly = true)
    public List<WinnerDtoWithoutDates> findWinnerByCampaignId(Integer id) {
        List<Winner> winnerList = new ArrayList<>();
        winnerDao.findWinnerByCampaignId(id).forEach(winnerList::add);
        if (winnerList.isEmpty()) {
            throw new GenericEmptyListException();
        }
        return winnerList.stream()
                .map(this::transformFromWinnerToWinnerDtoWithoutDates).collect(Collectors.toList());
    }

    @Transactional
    public List<WinnerDtoWithoutDates> findWinnersFromLastCampaignWithoutDates() {
        int lastCampaign = winnerDao.findLastCampaignId();
        List<Winner> winnerList = new ArrayList<>();
        winnerDao.findWinnerByCampaignId(lastCampaign).forEach(winnerList::add);
        if (winnerList.isEmpty()) {
            throw new GenericEmptyListException();
        }
        return winnerList.stream()
                .map(this::transformFromWinnerToWinnerDtoWithoutDates).collect(Collectors.toList());
    }

    public List<WinnerDtoWithoutDates> findAllWithoutDates() {
        List<Winner> winnerList = new ArrayList<>();
        winnerDao.findAll().forEach(winnerList::add);
        if (winnerList.isEmpty()) {
            throw new GenericEmptyListException();
        }
        return winnerList.stream()
                .map(this::transformFromWinnerToWinnerDtoWithoutDates).collect(Collectors.toList());
    }
}
