STEFAN ALEXANDRU 325CB

**BONUS:**
    Alte cazuri limita:
    1.Follow User By Id - nu ar trebui sa te poti urmari pe tine insuti
    2.Delete Post - ar trebuie sa se stearga comentariile postarii, like-urile postarii si like-urile 
aferente comentariilor postarii
    3.Delete Comment - ar trebuie sa se stearga like-urile comentariului
    4.Get Followin Post - ar trebui sa apara un mesaj daca nu urmarim pe nimeni, sau daca persoanele pe care
le urmarim nu au postari, de exemplu: "You don't follow anyone"/"The users you follow haven't posted
anything yet"
    5.Get User Posts - sa se afiseze un mesaj daca utilizatorul respectiv nu are postari spre exemplu
"The user you are trying to list posts from hasn't posted anything yet"
    6.Get Following - ar trebui sa se afiseze in mesaj daca nu urmaresti pe nimeni, spre exemplu
"You don't follow anyone"
    7.Get Following - ar trebui sa se afiseze in mesaj daca nu te urmareste nimeni, spre exemplu
"No one follows you"

**EXPLICATIE:**
    Pentru inceput am creat o clasa ce Command ce se ocupa cu gestionatul comenziilor si a
argumentelor primite ca parametru. In aceasta clasa Command se afla metode pentru toate
actiunile pe care un utilizator le poate face, dar si metoda execute ce se ocupa cu
selectarea metodei care trebuie utilizata.
    1.Create User - se verifica daca au fost primite argumentele corect, iar daca totul e ok
se creeaza un utilizator si se salveaza username-ul si parola acestuia in fisierul users.csv in
formatul "Username,Password"
    2.Create Post - se verifica ca userul sa fie valid utilizand metoda verifyAuth() care v-a mai
fi utilizata si ulterior in celelalte comenzi. Dupa verificarea userului se verifica daca toti
parametrii au fost primiti ok si daca textul are mai mult de 300 de caracter, dupa care se creeaza
postarea si se salveaza in fisierul posts.csv in formatul "Id,Post,Text". De asemenea, postarile isi
primesc id-ul dupa un atribut static pe care clasa il care ce se incrementeaza doar dupa fiecare
creere de postare.(daca o postare se sterge contorul NU se decrementeaza)
    3.Delete Post By Id - se verifica autentificare si parametrii, iar apoi se sterge postarea din
fisier doar daca utilizatorul detine acea postare
    4.Follow User By Username - se verifica autentificarea si parametrii, dupa care se verifica
daca utilizatorul pe care vrem sa il urmarim exista. Daca exista se verfica ca nu cumva sa il 
urmarim deja, iar apoi se adauga urmarirea in fisieru follow.csv in formatul "Follower,Followed"
    5.Unfollow User By Username - se verifica autenificare si parametrii, apoi se verifica daca
username-ul caruia vrem sa ii dam unfollow exista si il urmarim, in caz afirmativ se sterge din
fisierul follow.csv aceasta urmarire.
    6.Like Post - se verifica autentificarea si parametrii dupa care se verifica daca postarea
caruia vrem sa ii dam like exitsa, in caz afirmativ ne asiguram ca nu este deja apreciata postarea
deja de user, iar daca totul e in regula like-ul se salveaza in fisierul likePosts.csv in formatul
"PostID,PostOwner,LikedBy"
    7.Unlike Post - se verifica autentificarea si parametrii dupa care se sterge like-ul doar daca 
acesta exitsa.
    8. Like Comment - dupa verificare, se salveaza likeul la fel ca la Like Post
    9. Unlike Comment - ca la Unlike Post
    10. Get Following Post - dupa verificare se salveaza intr-un vector toate postarile persoanelor
urmarite si le afisam la final convorm formatului
    11. Get User Posts - la fel ca la Get Following Post, doar ca in loc de postarile pers urmarite,
sunt postarile utilizatorului specificat
    12. Get-Post-Details - se parcurg toate comentariile postarii si se retin intr-un vector, cat
si like-urile comentariilor si a postarii, iar la final se afiseaza toate conform formatului JSON
    13. Comment Post - dupa verificare, se salveaza comentariul in fisierul comments.csv in formatul
"Id,Username,Text"
    14. Delete Comment - dupa verificare, se sterge comentariul doar daca exista deja si este detinut
de utilizatorul care incearca sa faca aceasta actiune
    15. Get Following - se parcurg toate follow-urile si se retin intr-un vector doar cele care sunt
relevante, iar la final se afiseaza conform formatului
    16. Get Followers - ca la Get Following doar ca in loc de following afisam followers
    17. Get Most Liked Post - se parcurge fisierul likePosts si cu ajutorul unui vector de frecventa
aflam care sunt cele mai urmarite postari si le afisam doar pe primele 5
    18. Get Most Commented Post - la fel ca la Get Most Liked Post, doar ca se parcurge de data asta
fisierul comments.csv
    19. Get Most Followed Users - la fel ca la Get Most Liked Post, doar ca se parcurge de data asta
fisierul follow.csv, iar vectorul de frecventa este un hashmap, deoarece la postari ma ajutam de id-uri
pentru pozitia in vectorul de frecventa, iar din momemnt ce userii nu au id, folosesc username-ul lor
ca key in hasmap
    20. Get Most Liked Users - la fel ca la 19 doar ca de data asta avem in vizor fisierele likePosts
si likeComments
    21. Cleanup All - sterge continutul tuturor fisierelor in care se salveaza date