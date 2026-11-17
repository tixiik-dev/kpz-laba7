package lab10.spawn;

/** ConcreteCreator. */
public class SwampLocation extends Location {
    public SwampLocation() { super("Болото", 1); }

    @Override
    protected Monster createMonster() { return new Ghoul(); }
}
