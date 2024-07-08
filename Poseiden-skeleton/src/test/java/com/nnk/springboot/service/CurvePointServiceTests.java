package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.errors.UnknownCurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.service.impl.CurvePointServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

@SpringBootTest
class CurvePointServiceTests {

    @Autowired
    private CurvePointService curvePointService;

    @MockBean
    private CurvePointRepository curvePointRepository;

    @BeforeEach
    public void setUp() {
        curvePointRepository = Mockito.mock(CurvePointRepository.class);
        curvePointService = new CurvePointServiceImpl(curvePointRepository);
    }

    @Test
    void saveCurvePoint_savePoint_callSave() {
        // GIVEN a curve point
        final var curvePoint = new CurvePoint(10, 10d, 30d);

        // WHEN saving the curve point
        curvePointService.saveCurvePoint(curvePoint);

        // THEN the save method is called
        Mockito.verify(curvePointRepository, Mockito.times(1)).save(curvePoint);
    }

    @Test
    void findAllCurvePoints_findAllPoints_callFindAll() {
        // GIVEN no parameters
        // WHEN finding all curve points
        curvePointService.findAllCurvePoints();

        // THEN the findAll method is called
        Mockito.verify(curvePointRepository, Mockito.times(1)).findAll();
    }

    @Test
    void findCurvePointById_findPointById_callFindById() {
        // GIVEN an id
        final var id = 10;

        // WHEN finding a curve point by id
        Mockito.when(curvePointRepository.findById(id)).thenReturn(Optional.of(new CurvePoint(id, 10d, 30d)));
        final var point = curvePointService.findCurvePointById(id);

        // THEN the findById method is called
        Mockito.verify(curvePointRepository, Mockito.times(1)).findById(id);
        // AND the point is returned
        Assertions.assertTrue(point.isPresent());
    }

    @Test
    void findCurvePointById_unknownId_returnEmpty() {
        // GIVEN an unknown id
        final var id = 10;

        // WHEN finding a curve point by id
        Mockito.when(curvePointRepository.findById(id)).thenReturn(Optional.empty());
        final var point = curvePointService.findCurvePointById(id);

        // THEN the findById method is called
        Mockito.verify(curvePointRepository, Mockito.times(1)).findById(id);
        // AND no point is returned
        Assertions.assertTrue(point.isEmpty());
    }

    @Test
    void updateCurvePoint_updatePoint_throwException() {
        // GIVEN a curve point
        final var curvePoint = new CurvePoint(10, 10d, 30d);

        // WHEN updating the curve point
        // THEN an exception is thrown
        Assertions.assertThrows(UnknownCurvePoint.class, () -> curvePointService.updateCurvePoint(curvePoint));
    }

    @Test
    void updateCurvePoint_updatePoint_callSave() throws UnknownCurvePoint {
        // GIVEN a curve point
        final var curvePoint = new CurvePoint(10, 10d, 30d);

        // WHEN updating the curve point
        Mockito.when(curvePointRepository.existsById(curvePoint.getId())).thenReturn(true);
        curvePointService.updateCurvePoint(curvePoint);

        // THEN the save method is called
        Mockito.verify(curvePointRepository, Mockito.times(1)).save(curvePoint);
    }

    @Test
    void deleteCurvePointById_deletePoint_throwException() {
        // GIVEN an id
        final var id = 10;

        // WHEN deleting the curve point
        // THEN an exception is thrown
        Assertions.assertThrows(UnknownCurvePoint.class, () -> curvePointService.deleteCurvePointById(id));
    }

    @Test
    void deleteCurvePointById_deletePoint_callDelete() throws UnknownCurvePoint {
        // GIVEN an id
        final var id = 10;

        // WHEN deleting the curve point
        Mockito.when(curvePointRepository.existsById(id)).thenReturn(true);
        curvePointService.deleteCurvePointById(id);

        // THEN the delete method is called
        Mockito.verify(curvePointRepository, Mockito.times(1)).deleteById(id);
    }
}
