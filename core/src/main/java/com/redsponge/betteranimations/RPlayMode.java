package com.redsponge.betteranimations;

public enum RPlayMode {

    REGULAR {
        @Override
        public int getNext(int current, int length, Holder<Integer> extra, Holder<Boolean> done) {
            if(current + 1 == length) {
                done.value = true;
                return current;
            }
            return current + 1;
        }
    },
    PING_PONG {
        // extra is direction
        @Override
        public int getNext(int current, int length, Holder<Integer> extra, Holder<Boolean> done) {
            int next = current + extra.value;
            if(next < 0 || next == length) {
                extra.value *= -1;
                next = current + extra.value;
                done.value = true;
            }

            return next;
        }
    },
    LOOP {
        @Override
        public int getNext(int current, int length, Holder<Integer> extra, Holder<Boolean> done) {
            if(current + 1 == length) {
                done.value = true;
            }

            return (current + 1) % length;
        }
    }
    ;

    /**
     * @param current The switched frame
     * @param length The total length (in frames)
     * @param extra An extra integer, can be used as a direction etc...
     * @param done Change whether or not the animation is done
     * @return The next frame
     */
    public abstract int getNext(int current, int length, Holder<Integer> extra, Holder<Boolean> done);

}
