# Galiba App

Tento projekt je vyvinutý ako semestrálna práca pre predmet Vývoj aplikácií pre mobilné zariadenia na Žilinskej univerzite, Fakulte riadenia a informatiky. Hlavným cieľom tejto aplikácie je poskytnúť platformu pre malé kapely a umelcov na rýchle vytváranie akcií a koncertov a pre poslucháčov na ľahké nájdenie takýchto vystúpení.

### Úvod

Ako semestrálnu prácu sme si zvolili jednoduchú aplikáciu, ktorá bude slúžiť komunite menším kapelám/umelcom na rýchle vytváranie akcií/koncertov a poslucháčom na rýchle nájdenie takýchto interpretov/akcií menšieho rozmeru. Aplikácia bude závislá na internetovom pripojení používateľa pre nájdenie podujatia akéhokoľvek typu a jej hlavná myšlienka tak bude spropagovať čo najviac nezávislých, spontánnych koncertov konajúcich sa v blízkom okolí na jednom mieste.

## Dostupné aplikácie podobného zamerania

### Bandsintown

Bandsintown je známa aplikácia, ktorá pomáha používateľom sledovať svoje obľúbené kapely a dostávať notifikácie o ich nadchádzajúcich koncertoch. Tiež ponúka možnosti nákupu vstupeniek a integráciu s rôznymi hudobnými streamovacími službami.

### Soundkick

Soundkick, podobne ako Bandsintown, umožňuje používateľom sledovať svojich obľúbených umelcov a objavovať nové koncerty. Poskytuje personalizované odporúčania koncertov a pomáha používateľom priamo prostredníctvom aplikácie nakupovať vstupenky.

### Návrh architektúry aplikácie

Architektúra našej aplikácie je navrhnutá tak, aby bola užívateľsky prívetivá a efektívna, zabezpečujúc bezproblémový zážitok pre umelcov aj poslucháčov. Zahŕňa:

- **Frontend**: Mobilné rozhranie pre vytváranie a objavovanie akcií.
- **Backend**: Serverová časť na správu dát, autentifikáciu používateľov a správu akcií.
- **Databáza**: Google firebase firestore databáza na bezpečné ukladanie informácií o používateľoch a akciách.

## Zoznam použitých zdrojov

- [Material Design Components](https://m3.material.io/components) (Material design komponenty ktoré dotvárajú moderný štýl aplikácie)
- [Sheets Compose Dialogs](https://github.com/maxkeppeler/sheets-compose-dialogs) (Použité pre kalendár a hodiny Composables pretože v súčasnej dobe nie sú voľne dostupné priamo od Google)
- [Firebase Auth](https://firebase.google.com/docs/auth) (Firebase auth pre jednoduchý user login systém)
- [Firebase Firestore](https://firebase.google.com/docs/firestore) (Firebase firestore pre databázu použitú v app)
- [Firebase Storage](https://firebase.google.com/docs/storage) (Firebase storage pre ukladanie obrázkov online)
- Pomôcky pre prácu s Firebase:
  - [Adding Data to Firebase Firestore using Jetpack Compose](https://www.geeksforgeeks.org/android-jetpack-compose-add-data-to-firebase-firestore/?ref=ml_lbp)
  - [Adding Complex Queries to Jetpack Compose App](https://firebase.blog/posts/2023/12/adding-complex-queries-to-jetpack-compose-app/)
- [Špecifický komentár na Stack Overflow](https://stackoverflow.com/a/6018141) (Komentár ktorý mi dosť zjednodušil riešenie problému s implementáciou zobrazenia lokácie koncertu)
- [Bandsintown](https://www.bandsintown.com)
- [Soundkick](https://www.soundkick.com)

---

Vypracoval: Šimon Bartánus  
Študijná skupina: 5ZYS31  
Predmet: Vývoj aplikácií pre mobilné zariadenia  
Cvičiaci: doc. Ing. Patrik Hrkút, PhD.

Neváhajte prispieť k projektu alebo poskytnúť spätnú väzbu!
---

Vypracoval: Šimon Bartánus  
Študijná skupina: 5ZYS31  
Predmet: Vývoj aplikácií pre mobilné zariadenia  
Cvičiaci: doc. Ing. Patrik Hrkút, PhD.
