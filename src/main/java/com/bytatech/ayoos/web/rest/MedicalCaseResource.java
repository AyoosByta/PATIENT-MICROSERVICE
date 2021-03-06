package com.bytatech.ayoos.web.rest;
import com.bytatech.ayoos.service.MedicalCaseService;
import com.bytatech.ayoos.web.rest.errors.BadRequestAlertException;
import com.bytatech.ayoos.web.rest.util.HeaderUtil;
import com.bytatech.ayoos.web.rest.util.PaginationUtil;
import com.bytatech.ayoos.service.dto.MedicalCaseDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing MedicalCase.
 */
@RestController
@RequestMapping("/api")
public class MedicalCaseResource {

    private final Logger log = LoggerFactory.getLogger(MedicalCaseResource.class);

    private static final String ENTITY_NAME = "patientServiceMedicalCase";

    private final MedicalCaseService medicalCaseService;

    public MedicalCaseResource(MedicalCaseService medicalCaseService) {
        this.medicalCaseService = medicalCaseService;
    }

    /**
     * POST  /medical-cases : Create a new medicalCase.
     *
     * @param medicalCaseDTO the medicalCaseDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new medicalCaseDTO, or with status 400 (Bad Request) if the medicalCase has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/medical-cases")
    public ResponseEntity<MedicalCaseDTO> createMedicalCase(@RequestBody MedicalCaseDTO medicalCaseDTO) throws URISyntaxException {
        log.debug("REST request to save MedicalCase : {}", medicalCaseDTO);
        if (medicalCaseDTO.getId() != null) {
            throw new BadRequestAlertException("A new medicalCase cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MedicalCaseDTO result = medicalCaseService.save(medicalCaseDTO);
        return ResponseEntity.created(new URI("/api/medical-cases/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /medical-cases : Updates an existing medicalCase.
     *
     * @param medicalCaseDTO the medicalCaseDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated medicalCaseDTO,
     * or with status 400 (Bad Request) if the medicalCaseDTO is not valid,
     * or with status 500 (Internal Server Error) if the medicalCaseDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/medical-cases")
    public ResponseEntity<MedicalCaseDTO> updateMedicalCase(@RequestBody MedicalCaseDTO medicalCaseDTO) throws URISyntaxException {
        log.debug("REST request to update MedicalCase : {}", medicalCaseDTO);
        if (medicalCaseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MedicalCaseDTO result = medicalCaseService.save(medicalCaseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, medicalCaseDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /medical-cases : get all the medicalCases.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of medicalCases in body
     */
    @GetMapping("/medical-cases")
    public ResponseEntity<List<MedicalCaseDTO>> getAllMedicalCases(Pageable pageable) {
        log.debug("REST request to get a page of MedicalCases");
        Page<MedicalCaseDTO> page = medicalCaseService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/medical-cases");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /medical-cases/:id : get the "id" medicalCase.
     *
     * @param id the id of the medicalCaseDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the medicalCaseDTO, or with status 404 (Not Found)
     */
    @GetMapping("/medical-cases/{id}")
    public ResponseEntity<MedicalCaseDTO> getMedicalCase(@PathVariable Long id) {
        log.debug("REST request to get MedicalCase : {}", id);
        Optional<MedicalCaseDTO> medicalCaseDTO = medicalCaseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(medicalCaseDTO);
    }

    /**
     * DELETE  /medical-cases/:id : delete the "id" medicalCase.
     *
     * @param id the id of the medicalCaseDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/medical-cases/{id}")
    public ResponseEntity<Void> deleteMedicalCase(@PathVariable Long id) {
        log.debug("REST request to delete MedicalCase : {}", id);
        medicalCaseService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/medical-cases?query=:query : search for the medicalCase corresponding
     * to the query.
     *
     * @param query the query of the medicalCase search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/medical-cases")
    public ResponseEntity<List<MedicalCaseDTO>> searchMedicalCases(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of MedicalCases for query {}", query);
        Page<MedicalCaseDTO> page = medicalCaseService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/medical-cases");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
