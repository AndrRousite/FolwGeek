CharSequence formatted = Phrase.from("Hi {first_name}, you are {age} years old.")
  .put("first_name", firstName)
  .put("age", age)
  .format();

Send your phrase straight into a TextView:

Phrase.from("Welcome back {user}.")
  .put("user", name)
  .into(textView);

Comma-separated lists:

CharSequence formattedList = ListPhrase.from(", ")
  .format(1, 2, 3);
// returns "1, 2, 3"

English sentence-style lists:

ListPhrase listFormatter = ListPhrase.from(
  " and ",
  ", ",
  ", and ");

listFormatter.format(Arrays.asList(1, 2));
// returns "1 and 2"

listFormatter.format(Arrays.asList(1, 2, 3));
// returns "1, 2, and 3"
