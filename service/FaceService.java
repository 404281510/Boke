package edu.ahpu.boke.service;

import java.util.List;

import edu.ahpu.boke.domain.Face;

public interface FaceService {
	List<Face> findAllFaces();

	Face findDefaultFace();
}
