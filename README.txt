==========================================ProiectPOO partea 1===========================================
===============================================README===================================================

Riza Melih 324 CD


	Intrarea in program se realizeaza prin main ce primeste ca argument args in care este stocat
fisierul json de input si numele fisierului de output.

	Pentru citirea din fisierele json, realizez "erarhia" de clase imbricate unele de altele
din pachetul classestogettheinput, conform structurii de input.

	Clasele Action, Contain, Filter, Sort contin exact field-urile aferente
json-ului de input plus setter-e pentru stocarea datelor.


pachetul 'classestogettheinput':

	
	-> Clasa Credentials: contine atributele contului unui utilizator
		->name
		->password
		->accountType
		->country
		->balance
	

	-> Clasa Database: clasa de tipul Singleton realizeaza stocarea datelor, mai exact:
		->registeredUsers: lista cu utilzatorii inregistrati
		->listOfMovies: lista cu filmele existente pe platforma
		->accounts: hashmap ce realizeaza maparea nume-parola pentru toti utilizatorii 
					inregistrati


	-> Clasa Input: stocheaza din fisierul de input 
		->users: utilizatorii deja existenti pe platforma
		->movies: filmele existente pe platforma
		->actions: lista cu actiuni ce vor fi executate dintr-ul anumit fisier


	-> Clasa Movie: modeleaza caracteristicile unui film avand field-urile:
		name, year, duration, genres, actors, contriesBanned, numLikes, rating, 
		numRatings, sumRatings;
			-> Implementeaza clasa Comparable pentru realizarea sortarii 
		filmelor dupa durata si rating, suprascriind metoda CompareTo


	-> Clasa User: contine atributele unui utilizator:
		->credentials: instanta a clasei Credentials ce modeleaza detaliile contului
		->moviesList: lista de filme curent disponibile utilizatorului
		->searchMovie: lista cu filme disponibile dupa realizarea unui search
		->tokensCount: numarul de token-uri disponibile la un moment dat
		->numFreePremiumMovies: initializata initial la 15
		->purchasedMovies: lista cu filmele cumparate
		->watchedMovies: lista cu filmele vizionate
		->likedMovies: lista cu filmele apreciate
		->ratedMovies: lista cu filmele la care utilizatorul a acordat o "nota"
		->currentMovie: filmul curent la care este utilizatorul (folosit in actiunea
			de 'see details' pentru a avea control pe filmul la care ne referim)


	
	Din main dupa ce realizam stocarea datelor intr-o instanta a clasei Input, actiunile 
realizabile se impart in onPage si ChangePage. Astfel iteram prin toate actiunile oferite
si in functie de tipul acestora de apelam metoda 'exec' din Clasa ChangePageHandler sau din
OnPageHandler.


pachetul 'handler':

	-> Clasa ChangePageHandler (Singleton): folosit pentru a naviga de pe o pagina pe alta
		Contine field-urile:
			->input: actiunea de input preluata din main
			->user: utilizatorul curent
			->action: comanda de tip ChangePage ce trebuie executata
			->currentPage: pagina curenta pe care ne aflam
			->output: ArrayNode-ul in care se va realiza scrierea rezultatului
				unei actiuni
			->Instante ale claselor singleton pentru scris in output ("*ToJson") definite in 
		pachetul 'printtojsonoutput' (de ex: ErrorPrintToJson, SuccesPrintToJson, etc.) ce vor
		fi descrise mai jos

		->metoda 'exec()': realizeaza actiunea de schimbare a paginii in cazul in care este
permis acest lucru (pagina destinatie este "vecina" cu pagina curenta) si astfel currentPage
isi schimba valoarea in noua pagina, iar in caz contrar se afiseaza in 'output' eroare cu
ajutorul metodei 'writeToJson()' din clasa 'ErrorPrintToJson'. Metoda exec() intoarce un utilizator
(User) intrucat poate exista posibilitatea de logout si login pe alt cont deci, utilizatorul se
poate schimba.


	->Clasa OnPageHandler (Singleton): folosit pentru a realiza o actiune pe pagina curenta
		Contine field-urile:
			->action: actiunea de realizat pe pagina curenta
			->currentPage: pagina curenta pe care ne aflam
			->output: ArrayNode-ul in care se va realiza scrierea rezultatului
				unei actiuni
			->database: baza de date din care vom prelua filmele, conturile inregistrate.
			->user: utilizatorul curent
			->Instante ale claselor singleton pentru scris in output ("*ToJson") definite in 
		pachetul 'printtojsonoutput' (de ex: ErrorPrintToJson, SuccesPrintToJson, etc.) ce vor
		fi descrise mai jos

		->metoda 'exec()': realizeaza actiunea ceruta pe pagina curenta in cazul in care 
se poate (pagina curenta permite executia actiunii) si se printeaza eventual rezultatul actiunii
cu ajutorul metodelor 'writeToJson()' din clasele definite in pachetul 'printtojsonoutput', iar in 
caz contrar se printeaza eroarea.



	
pachetul 'printtojsonoutput'

	
	-> Clasa ErrorPrintToJson (Singleton): printeaza eroare in fiserul de output prin intremediul
		metodei 'writeToJson()'

	-> Clasa IterateInMoviesHelper: 
		metoda 'iterate(movies)': itereaza prin lista de filme date ca paramentru si 
realizeaza maparea caracteristicilor acestoara intr-un Arraynode ce va fi returnat. Si ulterior
adaugat in fisierul de output.

	-> Clasa JustLogedInToJson (Singleton): folosit pentru a printa in cazul in care se 
relog-eaza intr-un cont folosit anterior.

	-> Clasa SearchMovieToJson (Singleton): printeaza rezultatul in urma actiunii de 'search'

	-> Clasa SeeDetailsMoviesToJson (Singleton): printeaza rezultatul in urma actiunii de 'see details'

	-> Clasa SuccesPrintToJson (Singleton): utilizat in restul cazurilor neabordate anterior, metoda 
acestei clase este apelata cel mai des si realizeaza scrierea "completa" a unui utilizator.
		(ex: Cand se intra pe pagina de 'movies' si sunt printate toate filmele disponibile).
		Aceasta clasa apeleaza metoda 'iterate()' descrisa anterior.






























