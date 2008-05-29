package yaprnn.dvv;

import mlp.ActivationFunction;

public abstract class Data {
@SuppressWarnings("unused")
private enum use {TRAINING, TEST, NOT_USED}

public abstract double[] getData();
public abstract String getName();
public abstract String getLabel();
public abstract int getTarget();
public abstract String getFilename();
public abstract String getLabelFromTarget(int target);;
public abstract void subsample(int resolution, double overlap, ActivationFunction function);
public abstract void setTraining();
public abstract void setTest();
public abstract void setNotUsed();
public abstract boolean isTraining();
public abstract boolean isTest();
public abstract boolean isNotUsed();

}
