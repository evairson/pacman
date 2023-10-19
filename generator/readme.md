
## Comment utiliser le fichier guiToString.py</h2>

### Prérequis

Sont prérequis pour faire tourner le fichier :

- Python 3.9 (version 3.9.2 utilisée de mon côté, à vous de voir).
- Outil d'installation pip (Faire quelque chose comme `python3 -m pip install --upgrade pip`, à voir ou regarder sur [cette page](https://pip.pypa.io/en/stable/installation/) si jamais il y a des problèmes).
- Mettre à jour pip sinon à l'aide de la commande ci-dessus.
- Package tkinter à installer avec `pip install tk` (vous pouvez vérifier la bonne installation en entrant dans votre terminal `python3 -m tkinter`, si une petite fenetre s'affiche, c'est bon!).
- Package PySimpleGui à installer avec `pip install PySimpleGUI` (le package PySimpleGui tourne à l'aide de Tkinter donc il est impératif d'installer Tkinter avant sinon aie aie aie).

Si jamais vous avez des questions sur Tkinter ou PySimpleGui, ne pas hésiter à regarder leurs docs [ici](https://docs.python.org/3/library/tkinter.html) et [là](https://pypi.org/project/PySimpleGUI/).

### Faire tourner le script

Une fois en possession de tous les prérequis : 

- Se placer dans le répertoire où se trouve le script (normalement `*/cproj-vendredi-groupe1/generator/`).
- Lancer le script à l'aide de `python3 guiToString.py arg1 arg2 arg3 arg4` où :

	- arg1, arg2, arg3 et arg4 sont des entiers.
	- **arg1** est le nombre de lignes de la grille voulant être créée.
	- **arg2** est le nombre de colonnes de la grille voulant être créée.
	- **arg3** est la longueur des "cellules" utilisées sur la grille (ce sont des boutons).
	- **arg4** est la largeur des "cellules" utilisées sur la grille.

Par exemple, `python3 guiToString.py 5 5 2 2` créera une grille de taille 5x5, avec des unités de taille 2x2 (notons que la taille des boutons ne change en rien l'output, c'est purement graphique).

**Si vous ne donnez pas d'arguments** *(par exemple, `python3 guiToString`)* **ou que vous ne donnez pas le bon nombre d'arguments, la commande effectuée sera par défaut** `python3 guiToString 6 6 3 2`.

### Utiliser le script

On peut interagir de différents manières :

- Les cellules sans texte représentent les "coins" (ce sont des intersections entre les cellules). L'interface graphique n'étant pas très claire à utiliser, il faut s'entraîner un peu à l'utiliser pour visualiser la réalité.
- Les cellules "On"/"Off" sont des murs :
	- "On" signifie que le mur est activé
	- "Off" signifie que le mur est désactivé
	A noter que les murs fonctionnent par paire, c'est à dire que les murs unilatéraux (pouvant être traversés d'un seul côté mais pas de l'autre) n'existent pas pour l'instant. A noter aussi **les murs commutent en tore, c'est-à-dire que les murs du haut commutent avec ceux du bas et de même pour la gauche et la droite**
- Les cellules "Vide" représentent le contenu des cellules : on peut le paramétrer en utilisant le **menu déroulant en haut de la fenêtre** : aller dans **Cell** puis cliquer sur le bouton approprié pour associer au clic gauche de la souris le contenu en question. A noter que l'on ne peut pas placer plusieurs fois pacman ni les fantômes. Le bouton **Fill with pacgums** permet de mettre des pacgums en contenu de toutes les cases et de gagner énormément de temps!

Une fois le paramétrage fini, cliquer sur le menu **File**, puis **Export**, entrer le nom de fichier désiré, puis cliquer sur **Ok**. Le fichier texte est exporté dans le répertoire `/generator` sous le format `nomDuFichier.txt`

Voilà voilà c'est tout pour l'utilisation du script, si vous avez quelconque question n'hésitez surtout pas à me contacter je réponds vite en général (sauf si bien évidemment, je joue à LoL).  
Bisous,  
*Ama92*