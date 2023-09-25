import PySimpleGUI as sg

lines = columns = 8
main_layout = []

#North = 0
#South = 1
#East = 2
#West = 3

for i in range(lines):#lines
    layout_row = []
    #Generates north walls
    for j in range(columns):
        layout_row.append(sg.Button("Off",key=f"-WALL{i,j,0}-",size=(6,1),pad=((30,30),0),expand_x=False))
    main_layout.append(layout_row)


    layout_row = []
    #Generates east and west walls
    for j in range(columns):
        layout_row.append(sg.Button("Off",key=f"-WALL{i,j,3}-",size=(2,2),pad=((5,10),(10,10))))
        layout_row.append(sg.Button("Off",key=f"-CELL{i,j}-"))
        layout_row.append(sg.Button("Off",key=f"-WALL{i,j,2}-",size=(2,2),pad=((10,5),(10,10))))
    main_layout.append(layout_row)


    layout_row = []
    #Generates south walls
    for j in range(columns):
        layout_row.append(sg.Button("Off",key=f"-WALL{i,j,1}-",size=(6,1),pad=((30,30),0),expand_x=False))
    main_layout.append(layout_row)


#Generates states for walls : False = Empty, True = Full
states = {(i,j,orientation) : False for i in range(lines) for j in range(columns) for orientation in range(4)}

#Initializes the window
window = sg.Window("Test", main_layout)

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

        
        if not ((y == 0 and o == 3) or (y == columns - 1 and o == 2)):
            if o == 3:
                window[f"-WALL{x,y-1,2}-"].update(("On","Off")[value],button_color=("white",("blue","black")[value]))
                states[x,y-1,2] = states[x,y,o]
            if o == 2:
                window[f"-WALL{x,y+1,3}-"].update(("On","Off")[value],button_color=("white",("blue","black")[value]))     
                states[x,y+1,3] = states[x,y,o]

window.close()