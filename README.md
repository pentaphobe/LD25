## LD-25 - *No, Mister Bond...*

(The Ludum Dare theme was "You are the villain")

### Overview

In **No, Mister Bond** you play as an unnamed quintessential Bond Villain attempting to build and maintain your *Evil Lair*.

Unfortunately, MI-6 having realised their days out of the spotlight are through, have decided to try and rejuvinate their rockstar status by cloning their most successful secret agent.

Use an array of room traps, manage your evil income and at all costs:  **protect your self-destruct button**!

For the associative comprehenders:

It's like Dungeon Keeper or Tower Defense where you need to aim and activate weapons yourself.

Frantic (but fun!)
  
### Post-mortem note (full details will be blogged in the next couple of days)

This was my attempt for the 25th Ludum Dare game jam, unfortunately I didn't finish - partially because I pursued an uninspiring idea for the first 18 hours or so and then rapidly changed gears, and partially because I *really* don't know LibGdx at all (at all) and ended up implementing a lot of stuff that was likely available in the framework already (and in a more stable form).

I persisted and attempted to utilise the extra 24 hours provided to Jam entries (as opposed to the more strict "Compo"), but celebrating my friend's first complete project took priority.  As I write this it's 2 days after the Jam completed and last night I got to something vaguely resembling feature-completeness.  

Once the rush was off I allowed myself to noodle a little more, stopping to smell the ugly code flowers and playing around with some of the smaller gameplay aspects, it's nice to have a game which demands completion (if only so I can play it myself a bit more)

It's playable and, in my opinion pretty fun, but still needs a few things:

* Baseline feature requirements
  * Title and Completion screens
  * A menu to access some of the gameplay and graphical options
  * Serious code cleanup - all neatness and preparedness go out the window when you're in this much of a rush
* Gameplay stuff
  * Minions
  * Hero Bonds (tougher Bond clones with more health/speed/better weapons)
  * Dart rooms do nothing currently (other than cost you money)
  * Have a couple of short tutorial levels at the beginning, preferably adhering to the "You don't need a tutorial" edict.



## Original notes from during the compo

After much kerfluffle(sp?) I've decided to go with the bond villain game after all.

Burglary not only was drying up ideas-wise, but I also saw a post on the ludum dare stream of a screenshot which was *very* similar to what I was doing.

Hence, gear change.  It's gonna be a crunch

### Gameplay

You build your fortress out of rooms, rooms can be of a few different types.  Rooms are always the same size (5x5 for now) and always have a door to any adjacent room tiles.

#### Rooms

All rooms have doors and ```reset rates```.  They may also be damaged by **Bonds**.  

**Basic room components**:
- Hitpoints (upgradeable?)
- Doors (upgradeable)
  - hitpoints
- reset rate (upgradeable)
  - for firing rooms this is how long between shots, trap rooms it's how long before the room can be activated again

- Vanilla
  - just a room with doors
  - upgrades:
    - stronger doors
- Gas
  - vents which release toxic gas on villain activation
  - upgrades:
    - gas volume (number of vents)
- Darts
  - darts shoot out of the wall perpendicularly on villain activation
  - upgrades:
    - dart multifire (multiple shots before reset)
    - dart holes (1..4)
- Turrets
  - villain-controlled turrets aim where the mouse is
  - upgrades:
    - multiform (multiple shots before reset)
    - aim speed
    - turret count (1..4)
- Trapdoors
  - trapdoors spring open on activation
  - upgrades:
    - number of holes


#### Minions

Minions are just dumb guys that wander around.

They have terrible aim, are slow and stupid.

They can repair rooms and doors after a Bond does some damage

**Minion Upgrades:**

- Firepower
- Speed
- Accuracy
- Repair skill
