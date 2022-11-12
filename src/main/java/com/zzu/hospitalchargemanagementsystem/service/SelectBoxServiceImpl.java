package com.zzu.hospitalchargemanagementsystem.service;

import com.zzu.hospitalchargemanagementsystem.mapper.SelectBoxMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SelectBoxServiceImpl implements SelectBoxService {

    @Autowired
    private SelectBoxMapper selectBoxMapper;

    @Override
    public Integer insertDiseases(String diseases) {
        return selectBoxMapper.insertDiseases(diseases);
    }

    @Override
    public List<String> selectDiseases() {
        return selectBoxMapper.selectDiseases();
    }

    @Override
    public Integer insertProject(String project) {
        return selectBoxMapper.insertProject(project);
    }

    @Override
    public List<String> selectProject() {
        return selectBoxMapper.selectProject();
    }
}
