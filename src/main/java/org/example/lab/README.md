# Laboration 1 — Bibliotekshanteraren (CLI)

## Beskrivning
En konsolbaserad (CLI) Java-applikation för att administrera ett litet bibliotek.
Programmet körs i en loop och visar en meny tills användaren väljer att avsluta.

### Funktionalitet
- Lägga till en ny bok
- Registrera en ny medlem
- Låna en bok (med kontroll att boken inte redan är utlånad, och att medlemmen inte överskrider sitt lånelimit)
- Lämna tillbaka en bok
- Söka bok på (del av) titel eller författare, skiftlägesokänsligt
- Visa alla böcker med status (utlånad/tillgänglig och till vem)

## Projektstruktur
- `Book` — record som representerar en bok (isbn, titel, författare)
- `Member` — klass som representerar en medlem (id, namn, antal aktiva lån)
- `Library` — huvudklass som håller böcker och medlemmar i arrayer med fast storlek, samt håller reda på vilka böcker som är utlånade och till vem
- `Menu` — hanterar all interaktion med användaren (inläsning och utskrifter)
- `LibraryDemo` — programmets startpunkt, innehåller huvudloopen

## Hur man kör programmet
[Fyll i: t.ex. `mvn compile exec:java` eller hur du kör det i din IDE]

## Designval och reflektion

### Varför är `Book` en record men `Member` en vanlig klass?
`Book` är bara ren data (isbn, titel, författare) som aldrig ändras efter att
boken skapats, så det kändes naturligt att göra den till en record. `Member`
är annorlunda – den behöver ett fält (`activeLoans`) som ändras hela tiden när
någon lånar eller lämnar tillbaka en bok. En record tillåter inte att man
ändrar fälten efter att objektet skapats, så `Member` hade faktiskt inte
fungerat som en record ens om jag velat – det är inte bara ett krav i
uppgiften, utan en teknisk begränsning på vad `Member` behöver göra.

### Varför håller `Library` en separat array (`borrowedBy`) istället för ett
utlåningsfält direkt i `Book`?
Eftersom `Book` är immutable kan den inte ha ett fält som säger "utlånad:
ja/nej" som ändras när boken lånas ut – det hade gjort hela poängen med att
använda en record meningslös. Istället låter jag `Library` hålla en separat
array (`borrowedBy`) med samma index som `books`. Är `borrowedBy[i]` `null`
är boken ledig, annars pekar den på vilken medlem som lånat den. På så vis
slipper `Book` bry sig om lånestatus helt och hållet – det sköter `Library`
åt den.

### Hur säkerställer du att gränser som `activeLoans` inte kan kringgås?
Jag har helt enkelt ingen `setActiveLoans()`-metod – det finns inget sätt att
sätta värdet direkt utifrån. Istället går det bara att ändra via
`registerLoan()` och `registerReturn()`, som bara kan ändra värdet ett steg
i taget, och `canBorrowMore()` kollas alltid innan ett nytt lån tillåts. Utan
en direkt setter kan ingen "smyga förbi" reglerna och t.ex. sätta fler än tre
aktiva lån.

### Vilka avvägningar gjorde du kring array-kapacitet (fast storlek kontra
konstruktor-parameter)?
Jag valde att göra kapaciteten till en konstruktor-parameter
(`new Library(bookCapacity, memberCapacity)`) istället för att hårdkoda ett
tal i klassen. Största anledningen var att det blir mycket enklare att testa
"arrayen är full"-fallet – jag kan skapa ett litet bibliotek med plats för
typ 2 böcker istället för att behöva mata in 20 stycken för att trigga felet.
I det riktiga programmet skapar jag sedan biblioteket med de faktiska
siffrorna (x/x) i `main()`.