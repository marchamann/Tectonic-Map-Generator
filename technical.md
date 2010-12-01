# Tectonic Map Generator  (TMG)  

##What is it for?##

Tectonic Map Generatoris for people who love maps, geology,  imaginary worlds, or all three.  

It's purpose is to build randomized Earth-like world maps by simulating plate tectonics, erosion and hydrology (rivers and lakes).

My design goal was to produce aesthetically pleasing maps that were as realistic as possible but I was willing to sacrifice realism or technical accuracy to ensure that the program was computationally efficient and produced pleasing results.

A secondary goal was to allow the user to experiment with the program to see what kinds of maps they could produce.

Though I feel I have achieved both of these goals, there are still lots of refinements that could be added, and I may add these based on feedback and my own inclinations and time. 


##How does it work?##

###General Approaches###

###Discrete Methods###

One of my implementation goals was to use discrete methods as much as possible.  By discrete methods I mean methods that avoid the use of the real numbers, and techniques such as calculus that depend on them.

This is partly philosophical (perhaps I will write a separate essay about this), but mainly practical:  computers handle discrete representations more efficiently.

###Sinusoidal Separated Projection###

A big part of finding a solution that fit the "discrete methods" criterion was finding a mapping grid that could be represented by individual locations that were connected to each other in the standard cardinal directions.  This is not as easy as it sounds.

Most of my previous attempts at TMG were based on a rectangular grid, equivalent roughly to a cylindrical projection such as the Mercator projection.  The problem with this is the same problem that such a projection has:  it severly distorts things close to the poles.

Thanks to an [article](http://www.zompist.com/howto3.htm) by [Mark Rosenfelder](www.zompist.com), I got the idea to use a Sinusoidal Separated grid instead.  This has the advantage that it is closer to a true spherical representation of a world map.

The one catch, which I luckily found a fairly straight-forward solution to, was that movement is a little bit more tricky, because unlike a rectangular grid, there is not a unique north and south neighbour for each point on the map, and east-west movement must proceed more slowly near the poles than at the equator, or else points on the same longitude will get out of sync (and create weird spirals in the map.)

###Elevation Colour Scheme###

I found a pleasing colour scheme for colouring elevation maps at [USGS](http://www-atlas.usgs.gov/shadedm.html).

###Plate Generation###

I basically used a randomized flood-fill algorithm to make the starting plates.  The only drawback to this method is that as space is used up in the map, "races" between plates creats long thin "limbs" on the plate.  This may or may not be realistic, but it produces appealing starting shapes, and tends to be corrected by tectonic drift.  There are a couple of things I could try to "fix" this problem, but I may or may not experiment with them.

###Tectonic Drift###

This is the heart of the program, so I have the most to say here.  Maybe too much to say. ;-)  So I'll leave this section to be filled out later.

###Erosion###

To keep things simple and computationally efficient, the erosion algorith is fairly straight-forward.  For each location in the map, if that location has lower altitude neighbours, some of its elevation is transfered to those neighbours, up to a maximum.

This tends to create smoothed out maps, which is mostly realistic, but doesn't take into account climate, local geology, or other local conditions, so 100% realism is lost.

This may be an area for future refinement in the program.

Each tectonic drift iteration incorporates an erosion pass, but I have added the ability for users to apply iteration of erosion separately if they want to further smooth out the map.

Past versions of this program added an erosion process based on the hydrology process.  Basically, lakes tended to silt up and rivers wore things down.  This produced some nice realistic effects, but made each drift iteration too long.  I may re-implement this solution at a later date.

###Hydrology (Rivers and Lakes)###

This is simple in concept, but tricky in practice.  Water flows from high elevations to low elevations.  Low elevations fill up with water, and eventually all water ends up in the sea.

The big trick is dealing with adjacent land that is the same elevation.  It is very easy to end up with a "drainage cycle" where water flows around in circles, never getting to the sea.

