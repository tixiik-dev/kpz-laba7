package lab10.spawn;

/** ConcreteCreator. */
public class MountainLocation extends Location {
    public MountainLocation() { super("Гори", 3); }

    @Override
    protected Monster createMonster() { return new Troll(); }
}
