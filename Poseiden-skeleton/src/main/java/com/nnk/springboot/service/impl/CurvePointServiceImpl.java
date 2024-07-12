package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.errors.UnknownCurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.service.CurvePointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CurvePointServiceImpl implements CurvePointService {

    private final CurvePointRepository curvePointRepository;

    @Autowired
    public CurvePointServiceImpl(final CurvePointRepository curvePointRepository) {
        this.curvePointRepository = curvePointRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CurvePoint saveCurvePoint(final CurvePoint curvePoint) {
        return curvePointRepository.save(curvePoint);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CurvePoint> findAllCurvePoints() {
        return curvePointRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<CurvePoint> findCurvePointById(final Integer id) {
        return curvePointRepository.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CurvePoint updateCurvePoint(final CurvePoint curvePoint) throws UnknownCurvePoint {
        if (curvePointRepository.existsById(curvePoint.getId())) {
            return curvePointRepository.save(curvePoint);
        } else {
            throw new UnknownCurvePoint(curvePoint.getId());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteCurvePointById(final Integer id) throws UnknownCurvePoint {
        if (curvePointRepository.existsById(id)) {
            curvePointRepository.deleteById(id);
        } else {
            throw new UnknownCurvePoint(id);
        }
    }
}
