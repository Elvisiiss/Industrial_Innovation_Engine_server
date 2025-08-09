package com.IIE.Industrial_Innovation_Engine_server.mapper;


import com.IIE.Industrial_Innovation_Engine_server.entity.Tag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ToolMapper {
    List<Tag> getAllMyTags(Long id);
}
