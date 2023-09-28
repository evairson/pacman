import PySimpleGUI as sg

lines = columns = 6
main_layout = []

#Set size
sz = (2,2)

#North = 0
#South = 1
#East = 2
#West = 3

#TODO le système de transition, factoriser les trucs moches, Mettre les boutons vide en blanc ou autre, FAIRE UNE MENUBAR

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



#Generates states for walls : False = Empty, True = Full
states = {(i,j,orientation) : False for i in range(lines) for j in range(columns) for orientation in range(4)}
for i in range(lines):
    for j in range(columns):
        for k in range(4):
            if i == 0:
                states[i,j,0] = True
            if j == 0:
                states[i,j,3] = True
            if i == lines - 1:
                states[i,j,1] = True
            if j == columns - 1:
                states[i,j,2] = True

#Initializes the window
window = sg.Window("Test", main_layout,background_color="black")

#Event Loop
while True:
    event, values = window.read()
    if event == sg.WIN_CLOSED:
        break
    if event.startswith("-WALL"):
        print(event)
        x,y,o = int(event[6]), int(event[9]), int(event[12])
        print(x,y,o)
        value = states[x,y,o]
        states[x,y,o] = not value
        window[f"-WALL{x,y,o}-"].update(("On","Off")[value],button_color=("white",("blue","black")[value]))

        if not ((x == 0 and o == 0) or (x == lines - 1 and o == 1)):
            if o == 0:
                window[f"-WALL{x-1,y,1}-"].update(("On","Off")[value],button_color=("white",("blue","black")[value]))
                states[x-1,y,1] = states[x,y,o]
            if o == 1:
                window[f"-WALL{x+1,y,0}-"].update(("On","Off")[value],button_color=("white",("blue","black")[value]))     
                states[x+1,y,0] = states[x,y,o]

        
        if not ((y == 0 and o == 3) or (y == columns - 1 and o == 2)):#TODO correct bug with last left wall
            if o == 3:
                window[f"-WALL{x,y-1,2}-"].update(("On","Off")[value],button_color=("white",("blue","black")[value]))
                states[x,y-1,2] = states[x,y,o]
            if o == 2:
                window[f"-WALL{x,y+1,3}-"].update(("On","Off")[value],button_color=("white",("blue","black")[value]))     
                states[x,y+1,3] = states[x,y,o]

window.close()