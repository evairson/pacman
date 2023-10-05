import PySimpleGUI as sg

lines = 4
columns = 6
main_layout = []
#Name of the export file
exportName = []
#Set size
sz = (3,2)

#North = 0
#South = 1
#East = 2
#West = 3

#TODO  factoriser les trucs moches, Mettre les boutons vide en blanc ou autre, Système de d'arguments ligne de commande, Pacman et les autres prennent la place des pac gums terrible

#Top Screen menu definition
menu_def = [
   ['Cells', ['Pacgum', 'Energizer', 'Pacman', 'Blinky', 'Pinky','Inky','Clyde',"Fill with pacgums"]],
   ['File',['Export']]
]

#Permet de savoir quel action est liée au click
#0 = Pacgum
#1 = Energizer
#2 = Pacman
#3 = Blinky
#4 = Pinky
#5 = Inky
#6 = Clyde
setterCell = 0

#Définit les symboles
symbols = [".", "E", "pac","blk","pik","ink","cly"]

#Gère si pacman ou un fantome a déjà été placé
placed = {"pac":False, "blk":False, "pik":False, "ink":False, "cly":False}

#Rajoute le menu
main_layout.append([sg.Menu(menu_def)])

#Initalizing grid layout
for i in range(lines):#lines
    layout_row = []
    #Generates north walls
    for j in range(columns):
        if i == 0:
            layout_row.append(sg.Button(" ",size=sz,pad=(0,0),button_color=("blue"),border_width=0))
            layout_row.append(sg.Button("On",key=f"-WALL{i,j,0}-",size=sz,pad=(0,0),border_width=0,button_color = "blue"))
            layout_row.append(sg.Button(" ",size=sz,pad=(0,0),button_color=("blue"),border_width=0))
        elif j == 0:
            layout_row.append(sg.Button(" ",size=sz,pad=(0,0),button_color=("blue"),border_width=0))
            layout_row.append(sg.Button("Off",key=f"-WALL{i,j,0}-",size=sz,pad=(0,0),border_width=0,button_color = "black"))
            layout_row.append(sg.Button(" ",size=sz,pad=(0,0),button_color=("blue"),border_width=0))
        elif j == columns - 1:
            layout_row.append(sg.Button(" ",size=sz,pad=(0,0),button_color=("blue"),border_width=0))
            layout_row.append(sg.Button("Off",key=f"-WALL{i,j,0}-",size=sz,pad=(0,0),border_width=0,button_color = "black"))
            layout_row.append(sg.Button(" ",size=sz,pad=(0,0),button_color=("blue"),border_width=0))
        else:
            layout_row.append(sg.Button(" ",size=sz,pad=(0,0),button_color=("blue"),border_width=0))
            layout_row.append(sg.Button("Off",key=f"-WALL{i,j,0}-",size=sz,pad=(0,0),border_width=0,button_color = "black"))
            layout_row.append(sg.Button(" ",size=sz,pad=(0,0),button_color=("blue"),border_width=0))

    main_layout.append(layout_row)


    layout_row = []
    #Generates east and west walls
    for j in range(columns):
        if j == 0:
            layout_row.append(sg.Button("On",key=f"-WALL{i,j,3}-",size=sz,pad=(0,0),border_width=0,button_color = "blue"))
            layout_row.append(sg.Button("Cell",key=f"-CELL{i,j}-",size=sz,pad=(0,0),border_width=0,button_color = "red"))
            layout_row.append(sg.Button("Off",key=f"-WALL{i,j,2}-",size=sz,pad=(0,0),border_width=0,button_color = "black"))
        elif j == columns - 1:
            layout_row.append(sg.Button("Off",key=f"-WALL{i,j,3}-",size=sz,pad=(0,0),border_width=0,button_color = "black"))
            layout_row.append(sg.Button("Cell",key=f"-CELL{i,j}-",size=sz,pad=(0,0),border_width=0,button_color = "red"))
            layout_row.append(sg.Button("On",key=f"-WALL{i,j,2}-",size=sz,pad=(0,0),border_width=0,button_color = "blue"))
        else:
            layout_row.append(sg.Button("Off",key=f"-WALL{i,j,3}-",size=sz,pad=(0,0),border_width=0,button_color = "black"))
            layout_row.append(sg.Button("Cell",key=f"-CELL{i,j}-",size=sz,pad=(0,0),border_width=0,button_color = "red"))
            layout_row.append(sg.Button("Off",key=f"-WALL{i,j,2}-",size=sz,pad=(0,0),border_width=0,button_color = "black"))
    main_layout.append(layout_row)


    layout_row = []
    #Generates south walls
    for j in range(columns):
        if i == lines - 1:
            layout_row.append(sg.Button(" ",size=sz,pad=(0,0),button_color=("blue"),border_width=0))
            layout_row.append(sg.Button("On",key=f"-WALL{i,j,1}-",size=sz,pad=(0,0),border_width=0,button_color = "blue"))
            layout_row.append(sg.Button(" ",size=sz,pad=(0,0),button_color=("blue"),border_width=0))
        elif j == 0:
            layout_row.append(sg.Button(" ",size=sz,pad=(0,0),button_color=("blue"),border_width=0))
            layout_row.append(sg.Button("Off",key=f"-WALL{i,j,1}-",size=sz,pad=(0,0),border_width=0,button_color = "black"))
            layout_row.append(sg.Button(" ",size=sz,pad=(0,0),button_color=("blue"),border_width=0))
        elif j == columns - 1:
            layout_row.append(sg.Button(" ",size=sz,pad=(0,0),button_color=("blue"),border_width=0))
            layout_row.append(sg.Button("Off",key=f"-WALL{i,j,1}-",size=sz,pad=(0,0),border_width=0,button_color = "black"))
            layout_row.append(sg.Button(" ",size=sz,pad=(0,0),button_color=("blue"),border_width=0))
        
        else:
            layout_row.append(sg.Button(" ",size=sz,pad=(0,0),button_color=("blue"),border_width=0))
            layout_row.append(sg.Button("Off",key=f"-WALL{i,j,1}-",size=sz,pad=(0,0),border_width=0,button_color = "black"))
            layout_row.append(sg.Button(" ",size=sz,pad=(0,0),button_color=("blue"),border_width=0))

    main_layout.append(layout_row)



#Generates wallsStates for walls : False = Empty, True = Full
wallsStates = {(i,j,orientation) : False for i in range(lines) for j in range(columns) for orientation in range(4)}
for i in range(lines):
    for j in range(columns):
        for k in range(4):
            if i == 0:
                wallsStates[i,j,0] = True
            if j == 0:
                wallsStates[i,j,3] = True
            if i == lines - 1:
                wallsStates[i,j,1] = True
            if j == columns - 1:
                wallsStates[i,j,2] = True


cellsStates = {(i,j) : -1 for i in range(lines) for j in range(columns)}


#Initializes the window
window = sg.Window("Test", main_layout,background_color="black")

#Initializes the second window
def exportWindow():
    exportWindowLayout = [[sg.Text("Entrez le nom du fichier d'exportation")],
    [sg.Input(key = "-EXPORTINPUT-", expand_x=True), sg.Button("Ok", key = "-OKEXPORT-")]
    ]

    exportWindow = sg.Window('Export to file', exportWindowLayout)

    while True:
        event, values = exportWindow.read()
        if event == sg.WIN_CLOSED:
            break
        if event == '-OKEXPORT-':
            exportName.append(values["-EXPORTINPUT-"])
            exportWindow.close()
    exportWindow.close()

#Event Loop
while True:

    #Needed for event gestion
    event, values = window.read()

    #Finit le programme si la fenêtre est fermée
    if event == sg.WIN_CLOSED:
        break

    #Permet de gérer les events du menu permettant de set les cellules
    if event == "Pacgum":
        setterCell = 0
    if event == "Energizer":
        setterCell = 1
    if event == "Pacman":
        setterCell = 2
    if event == "Blinky":
        setterCell = 3
    if event == "Pinky":
        setterCell = 4
    if event == "Inky":
        setterCell = 5
    if event == "Clyde":
        setterCell = 6

    if event == "Fill with pacgums":
        for i in range(lines):
            for j in range(columns):
                cellsStates[i,j] = 0
                window[f"-CELL{i,j}-"].update(".")

    
    #Permet de gérer le changement des cellules

    if event.startswith("-CELL"):

        #Récupère les coordonnées de la cellule cliquée
        x,y = int(event[6]),int(event[9])


        if setterCell >= 2:
            if not placed[symbols[setterCell]]:
                cellsStates[x,y] = (x,y)
                window[f"-CELL{x,y}-"].update(symbols[setterCell])
                placed[symbols[setterCell]] = (x,y)
            
            else:
                window[f"-CELL{placed[symbols[setterCell]]}-"].update("Cell")
                cellsStates[x,y] = (x,y)
                placed[symbols[setterCell]] = (x,y)
                window[f"-CELL{x,y}-"].update(symbols[setterCell])


        else:
            #Affiche le changement à l'écran
            window[f"-CELL{x,y}-"].update(symbols[setterCell])

            #Met à jour le dictionnaire
            cellsStates[x,y] = setterCell


    #Permet de détecter les walls
    if event.startswith("-WALL"):

        #Récupère les coordonnées du wall cliqué 
        x,y,o = int(event[6]), int(event[9]), int(event[12])

        value = wallsStates[x,y,o]
        wallsStates[x,y,o] = not value
        window[f"-WALL{x,y,o}-"].update(("On","Off")[value],button_color=("white",("blue","black")[value]))


        #Commutativité des walls cote à cote
        if not ((x == 0 and o == 0) or (x == lines - 1 and o == 1)):
            if o == 0:
                window[f"-WALL{x-1,y,1}-"].update(("On","Off")[value],button_color=("white",("blue","black")[value]))
                wallsStates[x-1,y,1] = wallsStates[x,y,o]
            if o == 1:
                window[f"-WALL{x+1,y,0}-"].update(("On","Off")[value],button_color=("white",("blue","black")[value]))     
                wallsStates[x+1,y,0] = wallsStates[x,y,o]

        
        if not ((y == 0 and o == 3) or (y == columns - 1 and o == 2)):
            if o == 3:
                window[f"-WALL{x,y-1,2}-"].update(("On","Off")[value],button_color=("white",("blue","black")[value]))
                wallsStates[x,y-1,2] = wallsStates[x,y,o]
            if o == 2:
                window[f"-WALL{x,y+1,3}-"].update(("On","Off")[value],button_color=("white",("blue","black")[value]))     
                wallsStates[x,y+1,3] = wallsStates[x,y,o]


        #Commutativité des walls en tore
        if (x == 0 and o == 0):
            window[f"-WALL{lines-1,y,1}-"].update(("On","Off")[value],button_color=("white",("blue","black")[value]))
            wallsStates[lines-1,y,1] = wallsStates[x,y,o]
        if (x == lines - 1 and o == 1):
            window[f"-WALL{0,y,0}-"].update(("On","Off")[value],button_color=("white",("blue","black")[value]))
            wallsStates[0,y,0] = wallsStates[x,y,o]
        if (y == 0 and o == 3):
            window[f"-WALL{x,columns - 1,2}-"].update(("On","Off")[value],button_color=("white",("blue","black")[value]))
            wallsStates[x,columns - 1,2] = wallsStates[x,y,o]
        if (y == columns - 1 and o == 2):
            window[f"-WALL{x,0,3}-"].update(("On","Off")[value],button_color=("white",("blue","black")[value]))
            wallsStates[x,0,3] = wallsStates[x,y,o]

    #Exportation modules
    if event == "Export":
        exportWindow()
        window.close()


window.close()

output = []
for i in range(lines):
    line = []
    for j in range(columns):
        column = []
        for o in range(4):
            column.append([])
        line.append(column)
    output.append(line)

for i in range(lines):
    for j in range(columns):
        for o in range(4):
            if o == 0 or o == 1:
                if wallsStates[i,j,o]:
                    output[i][j][o] = "---"
                else:
                    output[i][j][o] = "   "
            if o == 2:
                if wallsStates[i,j,o]:
                    output[i][j][o] = "|"
                else:
                    output[i][j][o] = " "
            if o == 3:
                if wallsStates[i,j,o]:
                    output[i][j][o] = "| "
                else:
                    output[i][j][o] = " "

outstr = ""
for i in range(lines):
    for j in range(columns):
        outstr += "+" + output[i][j][0]
    outstr += "+"
    outstr += "\n"
    for j in range(columns):
        outstr += output[i][j][3]
        if cellsStates[i,j] == 0:
            outstr += ". "
        elif cellsStates[i,j] == 1:
            outstr += "E "
        else:
            outstr += "  "
        outstr += output[i][j][2]
    outstr += "\n"
    if i == lines-1:
        for j in range(columns):
            outstr+= "+" + output[i][j][1]
        outstr+="+"

newstr = outstr.replace("||","|")
newstr += "\n"

#Puts the (x, y) coos into the x,y format
def formatCoos(t):
    if t == False:
        return "error"
    return str(t)[1] +","+str(t)[4]

for i in range(2,7):
    current = symbols[i]
    newstr += f"{current.upper()}{formatCoos(placed[current])}\n"


with open(f"{exportName[0]}.txt","w") as f:
    f.write(newstr)