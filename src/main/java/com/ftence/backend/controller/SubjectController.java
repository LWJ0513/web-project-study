package com.ftence.backend.controller;

import com.ftence.backend.dto.Request.WikiRequestDTO;
import com.ftence.backend.dto.Response.SubjectListResponseDTO;
import com.ftence.backend.dto.Response.SubjectResponseDTO;
import com.ftence.backend.dto.Response.WikiResponseDTO;
import com.ftence.backend.service.SubjectService;
import com.ftence.backend.service.WikiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin(origins = "http://localhost:3000")

@RequiredArgsConstructor
@RestController
@RequestMapping("/subject")
public class SubjectController {

    private final SubjectService subjectService;
    private final WikiService wikiService;

    @GetMapping("/List")
    public List<SubjectListResponseDTO> getSubjectList() {
        return subjectService.getSubjectList();
    }

    @GetMapping("/{subjectName}")
    public SubjectResponseDTO getSubjectDetail(@PathVariable String subjectName) {
        return subjectService.getSubjectDetail(subjectName);
    }

    @GetMapping("/wiki/{subjectName}")
    public WikiResponseDTO getSubjectWiki(@PathVariable String subjectName) {
        return wikiService.getSubjectWiki(subjectName);
    }

    @PostMapping("/wiki")
    public ResponseEntity<Void> saveWikiInfo(@RequestBody WikiRequestDTO wikiRequestDTO){
        // wiki 버전이 같은 지 확인한다.
        boolean check = wikiService.checkWikiVersion(wikiRequestDTO);

        if (!check){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        // 버전이 똑같은 걸 확인하고 저장한다.
        wikiService.write(wikiRequestDTO);
        // 저장한 버전을
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
