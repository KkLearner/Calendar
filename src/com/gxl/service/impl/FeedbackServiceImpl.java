package com.gxl.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gxl.entity.Feedback;
import com.gxl.service.FeedbackService;

@Transactional
@Service("feedbackService")
public class FeedbackServiceImpl extends BaseServiceImpl<Feedback> implements FeedbackService {

}
