
public interface State {

    public State[] getChildren();
    public State getParent();
    public boolean equals(State other);
    public String toString();
    public float gValue();
    public int hashCode();
    public String solutionPath();
    public String solutionPathExtended();
    public float distanceToState(State otherState);
}