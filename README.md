# AND - Labo 4

## Auteurs
* Berney Alec
* Forestier Quentin
* Herzig Melvyn

## Questions

### <ins> 6.1 Quelle est la meilleure approche pour sauver le choix de l’option de tri de la liste des notes ? Vous justifierez votre réponse et l’illustrez en présentant le code mettant en oeuvre votre approche. </ins>
<br>

### <ins> 6.2 L’accès à la liste des notes issues de la base de données Room se fait avec une LiveData. Est-ce que cette solution présente des limites ? Si oui, quelles sont-elles ? Voyez-vous une autre approche plus adaptée ? </ins>
<br>

Cette solution présente des limites au niveau du nombre de données stockées et de la mémoire utilisée par les LiveData.
Pour illustrer ceci, prenons notre cas de laboratoire, nous avons la requête suivante pour récupérer les notes:
````
@Query("SELECT * FROM note")
````
Cela va charger toutes les notes dans la LiveData en mémoire. Si une grande quantité de données est présente, cela pourrait causé de gros problèmes de performance pour le smartphone utilisant le programme.
Cette solution n’est clairement pas adapté à de grandes bases de données!

Pour contourner cette limitation, 2 options sont possibles:
* Mettre en place de la pagination avec [_Paging de Jetpack_](https://developer.android.com/topic/libraries/architecture/paging/v3-overview)
* Utiliser un [_Flow_](https://developer.android.com/codelabs/basic-android-kotlin-training-intro-room-flow#0)

### <ins> 6.3 Les notes affichées dans la RecyclerView ne sont pas sélectionnables ni cliquables. Comment procéderiez-vous si vous souhaitiez proposer une interface persmettant de sélectionner une note pour l’éditer ? </ins>
<br>
