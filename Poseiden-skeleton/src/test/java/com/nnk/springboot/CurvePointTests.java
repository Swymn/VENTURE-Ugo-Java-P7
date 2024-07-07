package com.nnk.springboot;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CurvePointTests {

	@Autowired
	private CurvePointRepository curvePointRepository;

	@Test
	void curvePointTest() {
		var curvePoint = new CurvePoint(10, 10d, 30d);

		// Save
		curvePoint = curvePointRepository.save(curvePoint);
		Assertions.assertNotNull(curvePoint.getId());
        Assertions.assertEquals(10, (int) curvePoint.getCurveId());

		// Update
		curvePoint.setCurveId(20);
		curvePoint = curvePointRepository.save(curvePoint);
        Assertions.assertEquals(20, (int) curvePoint.getCurveId());

		// Find
		final var listResult = curvePointRepository.findAll();
        Assertions.assertFalse(listResult.isEmpty());

		// Delete
		final var id = curvePoint.getId();
		curvePointRepository.delete(curvePoint);
		final var curvePointList = curvePointRepository.findById(id);
		Assertions.assertFalse(curvePointList.isPresent());
	}

}
