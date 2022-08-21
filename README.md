Használt technológiák: Spring Boot, Spring Security, Thymeleaf, MySQL, JPA, JDBC, JavaScript

A program egy MySQL adatbázist használ, én a sajátomat "netbank" néven neveztem el, ahogy az application.properties fájlban látszik

A tesztelés során 4 felhasználót használtam. Ebből 3 sima user és 1 admin
Az adminnak nem adtam egyenleget és számlaszámot, mivel nem magánszemélyként kezeltem

A bejelentkezés oldalon email cím és jelszó megadásával léphetünk be. Ha ezt kétszer elrontjuk, akkor már csak Captcha kipipálásával próbálkozhatunk
(A captcha egy nagyon egyszerű checkbox, localStorage-ben tárolom a próbálozásokat)

Admin jogok:
-Bejelentkezés után rá tud keresni userekre email cím vagy számlaszám alapján
-Keresés után látja, hogy az adott user kinek utalt illetve kik utaltak neki
-A kilistázott tranzakciók közül mindet törölheti, törlés esetén a személy aki utalta az összeget azt visszakapja, akinek utalták attól levonják

User jogok:
-Bejelentkezés után a főoldalra kerül, ahol látja, hogy mekkora összeg áll rendelkezésre a számláján
-Van egy tranzakció fül a navigációs sávon, ami kilistázza az általa utalt és neki utalt tranzakciókat
-Ezen tranzakciók közül csak azokat törölheti amiket ő utalt és amik nem régebbiek 7 napnál, tranzakció törlésekor ugyanaz történik, mint az adminnál (szúrtam be egy példát is)
-Az utalás fülre kattintva megadhatunk egy email-t és egy összeget, így utalhatunk a rendszerben tárolt többi usernek
-Ha nem található email-t vagy az összeg nagyobb, mint amekkora a számláján van, akkor hibát dob az alkalmazás (negatív számok is le vannak kezelve)
