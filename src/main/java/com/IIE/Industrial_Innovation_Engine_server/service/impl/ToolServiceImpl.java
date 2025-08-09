package com.IIE.Industrial_Innovation_Engine_server.service.impl;

import com.IIE.Industrial_Innovation_Engine_server.dto.BaseResponse;
import com.IIE.Industrial_Innovation_Engine_server.entity.Tag;
import com.IIE.Industrial_Innovation_Engine_server.mapper.ToolMapper;
import com.IIE.Industrial_Innovation_Engine_server.service.ToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToolServiceImpl implements ToolService {

    private final ToolMapper toolMapper;

    @Autowired
    public ToolServiceImpl(ToolMapper toolMapper) {
        this.toolMapper = toolMapper;
    }

    @Override
    public BaseResponse getAllMyTags(Long id) {
        List<Tag> tags = toolMapper.getAllMyTags(id);
        return BaseResponse.success("获取所有我的标签",tags);
    }
}
