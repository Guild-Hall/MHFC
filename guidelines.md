## How to contribute:

Below you will find guidelines you are expected to keep when you want to contribute to this project.

## Naming guidelines:

#### variables: lowerCamelCase

   - be as distinctive as possible. A name such as `entity` doesn't say much. `targetEntity` is much better.
   A good rule of thumb: if there is another variable in scope that could have the same name, rename them both to
   something that highlights the difference between them. If there is only one variable EntityPlayer in scope,
   `player` is fine, when there are multiple, you could use `localPlayer` or `partyLeader`.
 
   - don't use articles in names: `thePlayer` is not better than just `player`. An article doesn't add meaning
   to a name, avoid them.
 
   - keep it short: variable names longer than ~20 characters are too long. Try to find synonyms to cut down on the
   length. That being said, if you can't find those, it's okay where it helps distinction
 
   - loop variables: loop counters should have names `i`, `j`, `k`, ... All other loop variables should have normal
   variable names
 
   - lambda variables: In a lambda, it is okay to use one-letter variables only when you use a statement, not a block
   as execution body. The letter should be the starting letter of the long name you'd give the variable normally.

#### methods: lowerCamelCase

   - a method almost always executes an action. Thus, there should be a verb in the name. `mean` could be
   `computeMean`, etc... Prefer strong verbs such as `build`, `assemble` over `make`, `do`.
 
   - you may ignore rule one where a usage of the method still reads a bit like an english sentence. E.g.
   `Set.of("a", "b")` is fine, even though the method is named `of`. Most often, when there is no verb in the
   name, there will be a preposition.

#### classes: UpperCamelCase

   - should resemble a noun, possibly with adjectives prepended. `DamageCalculator`, `FlyingEntity`, etc...
 
   - don't repeat yourself: do not include parts of the package name in the class name. If your class is in a package
   called `services`, name it just `ResourceLoader`, not `ResourceLoaderService`. Sometimes, when the
   class name would be especially short and indistinguishable in the project, you may ignore this rule. 
 
   - the above rule also applies to nested classes

## Coding guidelines:

 - Be aware of visibility of your variables. `public` is almost never what you want, how about `public final`?
 Even better is a public getter method, since that allows you to change how the value is stored in the future without
 having to change how it is accessed everywhere.

 - The above principle of prefering getter method is also effective for protected and package protected variables.
 
 - Having just mentioned package protected variables, try not to use them extensively. They force users to be in the
 same package as the declaration and have pretty much no benefit over protected.
 
 - Keep methods short. Everything over 20-30 lines of code in one method should get closer look. Try to split it into
 multiple methods. This makes it easier to read, debug and bisect in the head of the reader.
 
 - No magic constant: If a constant appears in the code, it should almost always at least be a private static final
 variable. Excluded are constants that are instantly clear from the surrounding code, but even here, be vary - if you
 have to use them multiple times, extract them into a variable. For example instead of `if(angle < 10)` have
 `private static final float CUTOFF_ANGLE = 10f;` and test `if(angle < CUTOFF_ANGLE)`. On the other
 hand `angle - 180` is probably clear from the surrounding code.
 
 - Keep nesting to a minimum: Instead of having a `if(condition)` guard the whole method body, have a
 `if(!condition) { return; }` at the top. This makes it easier to read, since the reader never has to wonder what
 you might do if the condition is false. This rule generalizes to:
 
 - Keep the special, unintuitive or short branches of the code first. It is encouraged to exit the method as fast
 (in terms of lines) as possible in those cases. If the reader is focused on one special problem, e.g. in debugging, he
 will find more quickly what he searches for.
 
 - No code duplication: everytime you press copy and paste and don't substantially modify the pasted code, think if you
 could have created a helper method with one or two parameters where you would have to write the code only once.
