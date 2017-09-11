package com.nuoxin.virtual.rep.virtualrepapi.dao;

import com.nuoxin.virtual.rep.virtualrepapi.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by fenggang on 9/11/17.
 */
public interface QuestionRepository extends JpaRepository<Question,Long>,JpaSpecificationExecutor<Question> {

}
