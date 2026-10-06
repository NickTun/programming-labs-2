import ru.ifmo.se.pokemon.*;

public class Main {
    public static void main(String[] args) {
        Battle battle = new Battle();

        battle.addAlly(new Spinarak("Спайк", 1));
        battle.addAlly(new Vespiquen("Улей", 21));
        battle.addAlly(new Shiftry("Тэнгу", 14));

        battle.addFoe(new Weezing("Смог", 35));
        battle.addFoe(new Rhyhorn("Рог", 1));
        battle.addFoe(new Rhydon("Дрель", 42));

        battle.go();
    }
}

final class Spark extends PhysicalMove {
    Spark() {
        super(Type.ELECTRIC, 65, 1.0);
    }

    @Override
    protected void applyOppEffects(Pokemon p) {
        if (Math.random() < 0.3) {
            Effect.paralyze(p);
        }
    }

    @Override
    protected String describe() {
        return "использует Spark";
    }
}

final class DoubleTeam extends StatusMove {
    DoubleTeam() {
        super(Type.NORMAL, 0, 1.0);
    }

    @Override
    protected void applySelfEffects(Pokemon p) {
        p.addEffect(new Effect().stat(Stat.EVASION, 1));
    }

    @Override
    protected String describe() {
        return "использует Double Team";
    }
}

final class Extrasensory extends SpecialMove {
    Extrasensory() {
        super(Type.PSYCHIC, 80, 1.0);
    }

    @Override
    protected void applyOppEffects(Pokemon p) {
        if (Math.random() < 0.1) {
            Effect.flinch(p);
        }
    }

    @Override
    protected String describe() {
        return "использует Extrasensory";
    }
}

final class Waterfall extends PhysicalMove {
    Waterfall() {
        super(Type.WATER, 80, 1.0);
    }

    @Override
    protected void applyOppEffects(Pokemon p) {
        if (Math.random() < 0.2) {
            Effect.flinch(p);
        }
    }

    @Override
    protected String describe() {
        return "использует Waterfall";
    }
}

final class Overheat extends SpecialMove {
    Overheat() {
        super(Type.FIRE, 130, 0.9);
    }

    @Override
    protected void applySelfEffects(Pokemon p) {
        p.addEffect(new Effect().stat(Stat.SPECIAL_ATTACK, -2));
    }

    @Override
    protected String describe() {
        return "использует Overheat";
    }
}

final class PoisonPowder extends StatusMove {
    PoisonPowder() {
        super(Type.POISON, 0, 0.75);
    }

    @Override
    protected void applyOppEffects(Pokemon p) {
        Effect.poison(p);
    }

    @Override
    protected String describe() {
        return "использует Poison Powder";
    }
}

final class Growl extends StatusMove {
    Growl() {
        super(Type.NORMAL, 0, 1.0);
    }

    @Override
    protected void applyOppEffects(Pokemon p) {
        p.addEffect(new Effect().stat(Stat.ATTACK, -1));
    }

    @Override
    protected String describe() {
        return "использует Growl";
    }
}

final class Rest extends StatusMove {
    Rest() {
        super(Type.PSYCHIC, 0, 1.0);
    }

    @Override
    protected boolean checkAccuracy(Pokemon att, Pokemon def) {
        return true;
    }

    @Override
    protected void applySelfEffects(Pokemon p) {
        p.setMod(Stat.HP, -(int) Math.round(p.getStat(Stat.HP) - p.getHP()));
        Effect.sleep(p);
    }

    @Override
    protected String describe() {
        return "использует Rest";
    }
}

final class FakeOut extends PhysicalMove {
    FakeOut() {
        super(Type.NORMAL, 40, 1.0, 3, 1);
    }

    @Override
    protected void applyOppEffects(Pokemon p) {
        Effect.flinch(p);
    }

    @Override
    protected String describe() {
        return "использует Fake Out";
    }
}

final class Magnitude extends PhysicalMove {
    private static final double[] POWER = {10, 30, 50, 70, 90, 110, 150};
    private static final double[] CHANCE = {0.05, 0.10, 0.20, 0.30, 0.20, 0.10, 0.05};

    Magnitude() {
        super(Type.GROUND, POWER[0], 1.0);
    }

    @Override
    protected double calcBaseDamage(Pokemon att, Pokemon def) {
        double roll = Math.random();
        double sum = 0;
        for (int i = 0; i < POWER.length; i++) {
            sum += CHANCE[i];
            if (roll < sum) {
                power = POWER[i];
                break;
            }
        }
        return super.calcBaseDamage(att, def);
    }

    @Override
    protected String describe() {
        return "использует Magnitude";
    }
}

final class IonDeluge extends StatusMove {
    IonDeluge() {
        super(Type.ELECTRIC, 0, 1.0, 1, 1);
    }

    @Override
    protected String describe() {
        return "использует Ion Deluge";
    }
}

final class Spinarak extends Pokemon {
    Spinarak(String name, int level) {
        super(name, level);
        setType(Type.BUG, Type.POISON);
        setStats(40, 60, 40, 40, 40, 30);
        setMove(new Spark(), new DoubleTeam(), new Extrasensory(), new Waterfall());
    }
}

final class Vespiquen extends Pokemon {
    Vespiquen(String name, int level) {
        super(name, level);
        setType(Type.BUG, Type.FLYING);
        setStats(70, 80, 102, 80, 102, 40);
        setMove(new Overheat(), new PoisonPowder(), new DoubleTeam());
    }
}

final class Shiftry extends Pokemon {
    Shiftry(String name, int level) {
        super(name, level);
        setType(Type.GRASS, Type.DARK);
        setStats(90, 100, 60, 90, 60, 80);
        setMove(new Overheat(), new PoisonPowder(), new DoubleTeam(), new Growl());
    }
}

final class Weezing extends Pokemon {
    Weezing(String name, int level) {
        super(name, level);
        setType(Type.POISON);
        setStats(65, 90, 120, 85, 70, 60);
        setMove(new Rest(), new FakeOut());
    }
}

class Rhyhorn extends Pokemon {
    Rhyhorn(String name, int level) {
        super(name, level);
        setType(Type.GROUND, Type.ROCK);
        setStats(80, 85, 95, 30, 30, 25);
        setMove(new Rest(), new FakeOut(), new Magnitude());
    }
}

final class Rhydon extends Rhyhorn {
    Rhydon(String name, int level) {
        super(name, level);
        setType(Type.GROUND, Type.ROCK);
        setStats(105, 130, 120, 45, 45, 40);
        setMove(new Rest(), new FakeOut(), new Magnitude(), new IonDeluge());
    }
}