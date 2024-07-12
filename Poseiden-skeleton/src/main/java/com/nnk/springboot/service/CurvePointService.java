package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.errors.UnknownCurvePoint;

import java.util.List;
import java.util.Optional;

/**
 * CurvePointService is an interface that defines the methods that will be implemented by the service.
 */
public interface CurvePointService {

    /**
     * Save a new CurvePoint
     * @param curvePoint the CurvePoint to save
     */
    CurvePoint saveCurvePoint(CurvePoint curvePoint);

    /**
     * Find all CurvePoint
     * @return a list of all CurvePoint
     */
    List<CurvePoint> findAllCurvePoints();

    /**
     * Find by id CurvePoint
     * @param id the id of the CurvePoint to get
     * @return the CurvePoint with the given id
     */
    Optional<CurvePoint> findCurvePointById(Integer id);

    /**
     * Update an existing CurvePoint
     * @param curvePoint the CurvePoint to update
     * @throws UnknownCurvePoint if the CurvePoint with the given id is not found
     */
    CurvePoint updateCurvePoint(CurvePoint curvePoint) throws UnknownCurvePoint;

    /**
     * Delete an existing CurvePoint
     * @param id the id of the CurvePoint to delete
     * @throws UnknownCurvePoint if the CurvePoint with the given id is not found
     */
    void deleteCurvePointById(Integer id) throws UnknownCurvePoint;
}
