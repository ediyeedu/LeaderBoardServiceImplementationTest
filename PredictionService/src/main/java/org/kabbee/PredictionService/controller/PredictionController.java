package org.kabbee.PredictionService.controller;

import lombok.RequiredArgsConstructor;
import org.kabbee.PredictionService.service.PredictionService;
import org.kabbee.PredictionService.service.dto.PredictionRequestDTO;
import org.kabbee.PredictionService.service.dto.PredictionResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/predictions")
public class PredictionController {
    private final PredictionService predictionService;

    @GetMapping("all")
    public ResponseEntity<?> getPredictions() {
        return ResponseEntity.ok().body(predictionService.getPredictions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPrediction(@PathVariable Long id) {
        PredictionResponseDTO prediction = predictionService.getPrediction(id);
        if(prediction == null) {
            return new ResponseEntity<>("Prediction not found", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok().body(prediction);
    }

    @PostMapping("create")
    public ResponseEntity<?> createPrediction(@RequestBody PredictionRequestDTO predictionDTO) {
        return ResponseEntity.ok().body(predictionService.createPrediction(predictionDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePrediction(@PathVariable Long id, @RequestBody PredictionRequestDTO predictionDTO) {
        PredictionResponseDTO prediction = predictionService.updatePrediction(id, predictionDTO);
        if(prediction == null) {
            return new ResponseEntity<>("Prediction not found", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok().body(prediction);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePrediction(@PathVariable Long id) {
        predictionService.deletePrediction(id);
        return ResponseEntity.ok().body("Prediction deleted successfully");
    }
}
