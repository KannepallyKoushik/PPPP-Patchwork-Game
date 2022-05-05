package com.patchwork.app.backend;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class PatchFactory {

    private ArrayList<Patch> patches;

    public void createTimeBoardPatches() {
        patches = new ArrayList<>(
                Arrays.asList(
                        // xxxx
                        //  x
                        new Patch(
                                Arrays.asList(
                                        Arrays.asList(true, true, true, true),
                                        Arrays.asList(false, true, false, false)
                                ),
                                3, 4, 1
                        ),
                        // xxx
                        // x
                        new Patch(
                                Arrays.asList(
                                        Arrays.asList(true, true, true),
                                        Arrays.asList(true, false, false)
                                ),
                                4, 6, 2
                        ),
                        // xxxxx
                        new Patch(
                                Collections.singletonList(
                                        Arrays.asList(true, true, true, true, true)
                                ),
                                7, 1, 1
                        ),
                        // xxxx
                        // xx
                        new Patch(
                                Arrays.asList(
                                        Arrays.asList(true, true, true, true),
                                        Arrays.asList(true, true, false, false)
                                ),
                                10, 5, 3
                        ),
                        // x
                        // xxxx
                        // x
                        new Patch(
                                Arrays.asList(
                                        Arrays.asList(true, false, false, false),
                                        Arrays.asList(true, true, true, true),
                                        Arrays.asList(true, false, false, false)
                                ),
                                7, 2, 2
                        ),
                        // xxxx
                        //  xx
                        new Patch(
                                Arrays.asList(
                                        Arrays.asList(true, true, true, true),
                                        Arrays.asList(false, true, true, false)
                                ),
                                7, 4, 2
                        ),
                        // xxx
                        new Patch(
                                Collections.singletonList(
                                        Arrays.asList(true, true, true)
                                ),
                                2, 2, 0
                        ),
                        //  x
                        // xxxx
                        //   x
                        new Patch(
                                Arrays.asList(
                                        Arrays.asList(false, true, false, false),
                                        Arrays.asList(true, true, true, true),
                                        Arrays.asList(false, false, true, false)
                                ),
                                2, 1, 0
                        ),
                        // xx
                        //  xx
                        new Patch(
                                Arrays.asList(
                                        Arrays.asList(true, true, false),
                                        Arrays.asList(false, false, true)
                                ),
                                3, 2, 1
                        ),
                        // xxxx
                        // x  x
                        new Patch(
                                Arrays.asList(
                                        Arrays.asList(true, true, true, true),
                                        Arrays.asList(true, false, false, true)
                                ),
                                3, 5, 1
                        ),
                        // xx
                        // x
                        new Patch(
                                Arrays.asList(
                                        Arrays.asList(true, true),
                                        Arrays.asList(true, false)
                                ),
                                3, 1, 0
                        ),
                        // xxx
                        //  x
                        new Patch(
                                Arrays.asList(
                                        Arrays.asList(true, true, true),
                                        Arrays.asList(false, true, false)
                                ),
                                2, 2, 0
                        ),
                        // xx
                        // xx
                        new Patch(
                                Arrays.asList(
                                        Arrays.asList(true, true),
                                        Arrays.asList(true, true)
                                ),
                                6, 5, 2
                        ),
                        //  x
                        // xxx
                        //  x
                        new Patch(
                                Arrays.asList(
                                        Arrays.asList(false, true, false),
                                        Arrays.asList(true, true, true),
                                        Arrays.asList(false, true, false)
                                ),
                                5, 4, 2
                        ),
                        // xx
                        // x
                        new Patch(
                                Arrays.asList(
                                        Arrays.asList(true, true),
                                        Arrays.asList(true, false)
                                ),
                                1, 3, 0
                        ),
                        // xx
                        //  xx
                        // xx
                        new Patch(
                                Arrays.asList(
                                        Arrays.asList(true, true, false),
                                        Arrays.asList(false, true, true),
                                        Arrays.asList(true, true, false)
                                ),
                                3, 6, 2
                        ),
                        // xx
                        //  xxx
                        new Patch(
                                Arrays.asList(
                                        Arrays.asList(true, true, false, false),
                                        Arrays.asList(false, true, true, true)
                                ),
                                2, 3, 1
                        ),
                        // xx
                        //  xx
                        new Patch(
                                Arrays.asList(
                                        Arrays.asList(true, true, false),
                                        Arrays.asList(false, true, true)
                                ),
                                7, 6, 3
                        ),
                        // x
                        // xxxx
                        //    x
                        new Patch(
                                Arrays.asList(
                                        Arrays.asList(true, false, false, false),
                                        Arrays.asList(true, true, true, true),
                                        Arrays.asList(false, false, false, true)
                                ),
                                1, 2, 0
                        ),
                        // x
                        // xxx
                        //  xx
                        new Patch(
                                Arrays.asList(
                                        Arrays.asList(true, false, false),
                                        Arrays.asList(true, true, true),
                                        Arrays.asList(false, true, true)
                                ),
                                8, 6, 3
                        ),
                        //  xx
                        // xxxx
                        //  xx
                        new Patch(
                                Arrays.asList(
                                        Arrays.asList(false, true, true, false),
                                        Arrays.asList(true, true, true, true),
                                        Arrays.asList(false, true, true, false)
                                ),
                                5, 3, 1
                        ),
                        //   x
                        // xxxxx
                        //   x
                        new Patch(
                                Arrays.asList(
                                        Arrays.asList(false, false, true, false, false),
                                        Arrays.asList(true, true, true, true, true),
                                        Arrays.asList(false, false, true, false, false)
                                ),
                                1, 4, 1
                        ),
                        // x
                        // xxx
                        new Patch(
                                Arrays.asList(
                                        Arrays.asList(true, false, false),
                                        Arrays.asList(true, true, true)
                                ),
                                4, 2, 1
                        ),
                        // xxxx
                        new Patch(
                                Collections.singletonList(
                                        Arrays.asList(true, true, true, true)
                                ),
                                3, 3, 1
                        ),
                        // xxx
                        //  x
                        //  x
                        new Patch(
                                Arrays.asList(
                                        Arrays.asList(true, true, true),
                                        Arrays.asList(false, true, false),
                                        Arrays.asList(false, true, false)
                                ),
                                5, 5, 2
                        ),
                        // xxx
                        //  x
                        // xxx
                        new Patch(
                                Arrays.asList(
                                        Arrays.asList(true, true, true),
                                        Arrays.asList(false, true, false),
                                        Arrays.asList(true, true, true)
                                ),
                                2, 3, 0
                        ),
                        // xxxx
                        // x
                        new Patch(
                                Arrays.asList(
                                        Arrays.asList(true, true, true, true),
                                        Arrays.asList(true, false, false, false)
                                ),
                                10, 3, 2
                        ),
                        // xxx
                        // xx
                        new Patch(
                                Arrays.asList(
                                        Arrays.asList(true, true, true),
                                        Arrays.asList(true, true, false)
                                ),
                                2, 2, 0
                        ),
                        // xxx
                        // x x
                        new Patch(
                                Arrays.asList(
                                        Arrays.asList(true, true, true),
                                        Arrays.asList(true, false, true)
                                ),
                                1, 2, 0
                        ),
                        //  xx
                        // xx
                        // x
                        new Patch(
                                Arrays.asList(
                                        Arrays.asList(false, true, true),
                                        Arrays.asList(true, true, false),
                                        Arrays.asList(true, false, false)
                                ),
                                10, 4, 3
                        ),
                        //  xxx
                        // xxx
                        new Patch(
                                Arrays.asList(
                                        Arrays.asList(false, true, true, true),
                                        Arrays.asList(true, true, true, false)
                                ),
                                4, 2, 0
                        ),
                        //  x
                        // xxxx
                        //  x
                        new Patch(
                                Arrays.asList(
                                        Arrays.asList(false, true, false, false),
                                        Arrays.asList(true, true, true, true),
                                        Arrays.asList(false, true, false, false)
                                ),
                                0, 3, 1
                        ),
                        // xx
                        new Patch(
                                Collections.singletonList(
                                        Arrays.asList(true, true)
                                ),
                                2, 1, 0
                        )
                )
        );
    }

    public ArrayList<Patch> getPatches() {
        if (patches == null) {
            createTimeBoardPatches();
        }

        return patches;
    }

    public Patch createSpecialPatch() {
        return new Patch(
                Collections.singletonList(Collections.singletonList(true)),
                0,
                0,
                0
        );
    }
}
