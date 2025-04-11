# Aplicația Festival App 

Această aplicație Java simulează o platformă de gestionare a unui festival de tip UNTOLD/Neversea, oferind atât funcționalități pentru participanți, cât și pentru organizatori. Aplicația este interactivă, bazată pe consolă și include funcții reale, dinamice și gamificate.

## Scopul aplicației

Festival App permite:

- gestionarea evenimentelor și programului festivalului;
- interacțiunea participanților cu zona FunZone;
- cumpărarea biletelor și rezervarea locurilor la evenimente;
- acumularea și cheltuirea de puncte în sistemul de gamificare;
- simularea unui mini-turneu între participanți.

Aplicația este structurată pe două categorii de utilizatori: **Participanți** și **Organizatori**, fiecare cu meniul și opțiunile sale.

---

## Meniul pentru Participanți

1. **Vizualizează toate biletele (inclusiv cu reduceri)**  
   Afișează toate biletele emise și aplică reduceri pentru persoanele sub 25 de ani.

2. **Participanți sub 25 de ani**  
   Listează participanții care beneficiază de bilete speciale cu reducere.

3. **Statistici de participare**  
   Afișează top 3 participanți cu cele mai multe participări și cel mai frecvent tip de eveniment.

4. **Seturi de DJ pe scena principală**  
   Arată concertele DJ-ilor care performează pe scena principală.

5. **Jocuri deschise toată noaptea în FunZone**  
   Filtrează doar jocurile din FunZone care funcționează toată noaptea.

6. **Cumpără un bilet**  
   Permite utilizatorului să își cumpere un bilet și să introducă datele personale.

7. **Rezervă loc la un eveniment cu locuri limitate (GlobalTalks)**  
   Permite unui participant să-și rezerve un loc la un talk special, în limita capacității.

8. **Caută evenimente care încep după o anumită oră**  
   Filtrare personalizată a evenimentelor în funcție de oră.

9. **Sistem de puncte - Câștigă și cheltuie**
    - Câștigi puncte automat la cumpărarea biletului (10% din preț).
    - Primești puncte suplimentare pentru participarea la CampEats, FunZone, GlobalTalks.
    - Poți cheltui punctele pe premii (ex: badge-uri, acces VIP).

10. **Participă la Mini-Turneu FunZone**
    - Te înscrii cu codul biletului tău.
    - Se generează aleator adversari.
    - Se simulează rundele (cu posibilitatea meciurilor 1v1 sau 3 participanți).
    - Câștigătorul final primește 50 de puncte bonus.

---

## Meniul pentru Organizatori

1. **Vezi programul complet pentru o anumită zi**  
   Afișează toate evenimentele dintr-o zi aleasă, în ordine cronologică.

2. **Vezi organizatorii și evenimentele asociate**  
   Afișează fiecare organizator și evenimentele pe care le gestionează.

3. **Grupează evenimentele după tip**  
   Grupa evenimentele în categorii: `Concert`, `DJ`, `CampEats`, `FunZone`, `GlobalTalks`.

4. **Ordonează evenimentele după ora de start**  
   Listează toate evenimentele de la festival în ordine cronologică.

5. **Mută un eveniment în altă zi**  
   Organizatorii pot modifica ziua unui eveniment, iar aplicația actualizează automat programul.

---

## Tehnologii utilizate

- Java 17
- OOP 
- Colecții generice (`List`, `Set`, `Map`)
- Design modular: separare între `Main` și `FestivalService`
- Sisteme de puncte și gamificare
- Meniu interactiv pe roluri

---

## 📌 Observații

- Aplicația este complet funcțională în linia de comandă.
- Toate datele sunt generate în metoda `initDemoData()` din `FestivalService.java`.

