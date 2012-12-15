## LD-25 - *No, Mister Bond...*
  
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