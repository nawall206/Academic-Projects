# ML Pressure Prediction (Keras Neural Network)

This project predicts the **target pressure (bar)** using a neural network (MLP) trained on sensor data.

## Overview
- Implemented a data preprocessing pipeline using **scikit-learn** (`ColumnTransformer`, `StandardScaler`, `SimpleImputer`).
- Trained a **Keras MLP model** with:
  - Adam optimizer
  - CosineDecayRestarts learning rate schedule
  - Dropout regularization
- Evaluated using **MAPE** and **R²** metrics.

## Best Model Results
- Train MAPE: 0.0999  
- Validation MAPE: 0.1336
- Test MAPE: 0.1380  
- R² ≈ 0.9460 
- Kaggle Score: 0.25

## Tools & Libraries
Python, TensorFlow/Keras, scikit-learn, pandas, numpy, matplotlib

## Files Included
- `ml_pipeline.ipynb` — Google Colab notebook
- `model.keras` — saved trained model
- `predictions.csv` — output predictions
- `README.md` — project documentation

## How to Run
1. Open `ml_pipeline.ipynb` in Google Colab or Jupyter.
2. Run all cells to preprocess data, train the model, and generate predictions.
3. The model will save outputs to `predictions.csv`.

## Notes
This project was completed as part of the **COMP3010 Machine Learning** coursework.
All code was implemented and tested in Google Colab.
