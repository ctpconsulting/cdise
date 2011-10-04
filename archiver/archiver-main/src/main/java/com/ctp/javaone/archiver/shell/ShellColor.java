package com.ctp.javaone.archiver.shell;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.Ansi.Color;


public enum ShellColor {
    
    NONE {
        @Override public Ansi applyTo(Ansi ansi) {
            return ansi;
        }
    }, 
    GREEN {
        @Override public Ansi applyTo(Ansi ansi) {
            return ansi.fg(Color.GREEN);
        }
    }, 
    RED {
        @Override public Ansi applyTo(Ansi ansi) {
            return ansi.fg(Color.RED);
        }
    }, 
    YELLOW {
        @Override public Ansi applyTo(Ansi ansi) {
            return ansi.fg(Color.YELLOW);
        }
    }, 
    BLUE {
        @Override Ansi applyTo(Ansi ansi) {
            return ansi.fg(Color.BLUE);
        }
    };

    abstract Ansi applyTo(Ansi ansi);

}
