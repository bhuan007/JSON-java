For Milestone 3, I added my own parse method to the XML class.

In order to prepend a certain string, I added a new parameter to the parse method, which is Function<String, String> keyTransformer.

keyTransformer will allow the user to create their own string manipulation method that will be applied during the inner parse

method of the XML class.

In Milestone 1, I did a similar method but outside of the JSON.org library. In comparison, instead of having to go through multiple

steps to prefix keys, manipulating the xml file inside the JSON.org library is much more efficient. For example, in my previous Milestone 1

prefixJSONObject method, I had to iterate through each json key through a recursive function.

