package com.haertz.be.designer.service;

import com.haertz.be.designer.repository.DesignerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DesignerService {
    private final DesignerRepository designerRepository;


}
