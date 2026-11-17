package lab10.spawn;

/** ConcreteCreator. */
public class ForestLocation extends Location {
    public ForestLocation() { super("Ліс", 3); }

    @Override
    protected Monster createMonster() { return new Wolf(); }
}
