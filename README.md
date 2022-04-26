# AND - Labo 4

## Auteurs
* Berney Alec
* Forestier Quentin
* Herzig Melvyn

## Questions

### <ins> 6.1 Quelle est la meilleure approche pour sauver le choix de l’option de tri de la liste des notes ? Vous justifierez votre réponse et l’illustrez en présentant le code mettant en oeuvre votre approche. </ins>
<br>

Une approche simple est d'utiliser les SharedPreference. En effet, elle est très simple à mettre en oeuvre.

On déclare les préférences
````
    /**
     * SharedPreferences to save the sorting order.
     */
    private lateinit var prefs: SharedPreferences
```` 

On utilise les préférences de l'activité liée au fragment
````
prefs = this.activity?.getPreferences(Context.MODE_PRIVATE) ?: return

```` 

Lorsque l'adapteur contenant la liste des notes se modifie, on vérifie l'état de préférence de l'ordre et l’on trie la liste, une fois qu'elle est remplie.
````
notesViewModel.allNotes.observe(viewLifecycleOwner) { value ->
            adapter.items = value

            val sortPrefs: String? = prefs.getString(SORT_PREF, null)

            if (sortPrefs == SORT_PREF_ETA) {
                sortByETA()
            } else if (sortPrefs == SORT_PREF_DATE) {
                sortByCreationDate()
            }

        }
````

Le fait de trier dès qu'un changement s'effectue dans la liste, permet également à ce qu'un nouvel élément soit trié dès son insertion.

Également, l'ordre de tri sera gardé lorsqu'une rotation d'écran s'effectue.

Étant donné la simplicité à la mise en oeuvre, cette méthode nous parait être la meilleure approche pour sauvegarder le choix de l'utilisateur. 
Il nous suffit d'enregistrer son choix dans les différentes méthodes de tri afin de sauvegarder son dernier choix.

```` 
    /**
     * Sort the displayed list by creation date
     */
    fun sortByCreationDate() {
        adapter.sortByCreationDate()
        prefs.edit {
            putString(SORT_PREF, SORT_PREF_DATE)
        }
    }

    /**
     * Sort the displayed list by ETA
     */
    fun sortByETA() {
        adapter.sortByETA()
        prefs.edit {
            putString(SORT_PREF, SORT_PREF_ETA)
        }
    }

```` 

### <ins> 6.2 L’accès à la liste des notes issues de la base de données Room se fait avec une LiveData. Est-ce que cette solution présente des limites ? Si oui, quelles sont-elles ? Voyez-vous une autre approche plus adaptée ? </ins>
<br>

Cette solution présente des limites au niveau du nombre de données stockées et de la mémoire utilisée par les LiveData.
Pour illustrer ceci, prenons notre cas de laboratoire, nous avons la requête suivante pour récupérer les notes:
````
@Query("SELECT * FROM note")
````
Cela va charger toutes les notes dans la LiveData en mémoire. Si une grande quantité de données est présente, cela pourrait causer de gros problèmes de performance pour le smartphone utilisant le programme.
Cette solution n’est clairement pas adaptée à de grandes bases de données!

Pour contourner cette limitation, 2 options sont possibles:
* Mettre en place de la pagination avec [_Paging de Jetpack_](https://developer.android.com/topic/libraries/architecture/paging/v3-overview)
* Utiliser un [_Flow_](https://developer.android.com/codelabs/basic-android-kotlin-training-intro-room-flow#0)

### <ins> 6.3 Les notes affichées dans la RecyclerView ne sont pas sélectionnables ni cliquables. Comment procéderiez-vous si vous souhaitiez proposer une interface permettant de sélectionner une note pour l’éditer ? </ins>
<br>

Les manipulations pour rendre les notes cliquables sont les suivantes:

 * Dans la déclaration de l'adapteur, nous définissons un argument (clickListener) pour récupérer une fonction qui représente un listener.

 ```
class NotesAdapter( private val clickListener: (NoteAndSchedule) -> Unit ) : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {
 ```

 > Pour faire plus proprement, nous aurions pu utiliser une interface. Mais pour la simplicité de ce tutoriel, nous allons continuer avec des lambdas.

 * Dans la déclaration du viewHolder, nous définissons une fonction en argument clickAtPosition.

 ```
 inner class ViewHolder(view: View, clickAtPosition: (Int) -> Unit) : RecyclerView.ViewHolder(view)
 ```

 * Dans le viewHolder définir le constructeur:

 ```
    init {
        itemView.setOnClickListener { 
            clickAtPosition(adapterPosition) 
        }
    }
```

* Modifier onCreateViewHolder pour passer une fonction au viewHolder:

```
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            SIMPLE -> ViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item_simple_note, parent, false)) {
                clickListener(items[it])
            }
            /* SCHEDULED */
            else -> ViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item_scheduled_note, parent, false)) {
                clickListener(items[it])
            }
        }
    }
```

* Dans le fragment, passer le listener à la création de l'adapteur:

```
    private val adapter = NotesAdapter() {
        Log.v(TAG, "notes title: " + it.note.title)
        // Intent to create activity of note edition should go here
    }
```

Cette méthode est plus compliquée que certains tutoriels qui travaillent uniquement depuis onBindViewHolder. Cependant, nous n'effectuons pas plusieurs appels à setOnClickListener.

```
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])

        holder.itemView.setOnClickListener {
            clickListener(items[position])
        }
    }
```

>[Référence](https://www.youtube.com/watch?v=GvLgWjPigmQ)
