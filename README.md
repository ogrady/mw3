
Metal Warriors 3
===

### Eclipse Setup

1. Pull the code
2. Open existing project in mw3/. (There should be a .project file already)
3. Add all mw3/lib/slick/*.jar files to the Libraries in the Build Path of the project.
4. Choose your native-*.jar for your OS from the Libraries in the Build Path of the project and unroll it to select "Native library location: ..."
5. Click "Edit..." and then "Workspace" to select the folder mw3/lib/slick/natives-*/ depending on your OS as the path to the native libraries.
6. Add rsc/ and src/ to the Source in the Build Path of the project.

### Coding Guidelines

* Generally all variables and methods should be written in camelCase
* Class-variables **must** start with an underscore (static variables aside)
* Class-variables **must** either be final, have getters and setters or have to be able to handle every possible value when they are supposed to be visible to the outside (e.g. the int-coordinate of a position can be public, as null is impossible and every numeric value is valid)
* Don't Repeat Yourself (DRY)
* Code should be self-explanatory. Same goes for parameters, class-variables, method-names etc.... but *fill in the JavaDoc* for each method (for getters, only the @return-part has to be filled in, for setters only the @param part if the setter itself doesn't do fancy stuff or disallow certain values)
* Parameters **should** be final whenever possible
* Warnings **should** be resolved as soon as possible (remove unused imports, initialize variables when needed, remove unused variables)
* Code **should** be structured whenever **feasible**. Don't waste hours to force your code to be structured but try to avoid switch-case-return and such
* Only push code that is working

So Features.

        Very Mech.

  Wow.

                    Much Making.
