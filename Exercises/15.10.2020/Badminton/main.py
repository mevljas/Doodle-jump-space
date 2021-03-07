from pygame import *
from reloadr import autoreload
import numpy as np
from random import random
import math
v = np.array

'''
TODO: Add nicer graphics.
'''







@autoreload
def load():
    assets['grass'] = image.load('assets/grass.png')
    assets['player'] = image.load('assets/player.png')
    assets['birdie'] = image.load('assets/birdie.png')



@autoreload
def init():
    assets['player_bounds'] = Rect(0, 290, 170, 200)
    assets['leg_bounds'] = Rect(0, 10, 180, 95)
    assets['leg_walk_bounds'] = Rect(0, 130, 180, 95)
    assets['arm_bounds'] = Rect(224, 187, 250, 160)
    assets['arm_low_bounds'] = Rect(210, 350, 262, 144)
    # WINSIZE[1] is window's height.
    data['ground_height'] = WINSIZE[1] - 75
    data['player_position'] = 300
    # Position vector
    data['birdie'] = { 'position': v([300.0, 100.0]), 'velocity': v([0.0, 0.0])}


    data['player_walking'] = False
    data['arm_low'] = False

    data['state'] = 'in_air'



@autoreload
def update():# If left mouse button is pressed
    velocity = 0
    arm_low = False


    prev_arm_low = data['arm_low']



    if mouse.get_pressed()[0]:
        # Get mouse x position
        new_position = mouse.get_pos()[0]
        velocity = (new_position - data['player_position']) / 5
        data['player_position'] += velocity

        if mouse.get_pos()[1] > data['ground_height'] - 75:
            arm_low = True

    swing = False
    if prev_arm_low and not arm_low:
        swing = True

    data['arm_low'] = arm_low

    if abs(velocity) > 0.1:
        data['player_walking'] = True
    else:
        data['player_walking'] = False

    if data['state'] == 'in_air':
        data['birdie']['velocity'] += v([0.0, 700.0]) * clock.get_time() * 0.001
        data['birdie']['position'] += data['birdie']['velocity'] * clock.get_time() * 0.001


        # Index one represent y coordinate
        if data['birdie']['position'][1] > data['ground_height']:
            data['state'] = 'on_ground'
            data['birdie']['position'][1] = data['ground_height']


    if data['state'] == 'on_ground' and abs(data['birdie']['position'][0] - data['player_position']) < 50:
        data['state'] = 'serve'

    if data['state'] == 'serve':
        data['birdie']['position'] = v([data['player_position'] + 60, data['ground_height'] - 130])

        if swing:
            data['state'] = 'in_air'
            data['birdie']['velocity'] = v([ random()*200-50, -900.0])

    for e in event.get():
        if e.type == QUIT:
            display.quit()

        elif e.type == MOUSEBUTTONDOWN:
            if e.button == 3:
                init()
            elif e.button == 2:
                load()

@autoreload
def draw():
    # Fill with white color
    screeen.fill(color.THECOLORS['white'])
    screeen.blit(assets['grass'], (0, data['ground_height'] - 16))
    screeen.blit(assets['player'], (data['player_position'] - 125, data['ground_height'] - 250), assets['player_bounds'])
    legs = 'leg_bounds'
    if time.get_ticks() % 500 < 250 and data['player_walking']:
        legs = 'leg_walk_bounds'
    screeen.blit(assets['player'], (data['player_position'] - 130, data['ground_height'] - 70), assets[legs])

    arms = 'arm_bounds'
    if data['arm_low']:
        arms = 'arm_low_bounds'

    screeen.blit(assets['player'], (data['player_position'] - 120, data['ground_height'] - 180), assets[arms])
    # screeen.blit(assets['birdie'], data['birdie']['position'] - 32)

    angle = math.degrees(math.atan2(-data['birdie']['position'][1], data['birdie']['position'][0]))

    screeen.blit(transform.rotate(assets['birdie'], angle), data['birdie']['position'] - 32)

    # Update the screen.
    display.update()

assets = {}
data = {}

clock = time.Clock()

# Prepare the window
WINSIZE = [600, 800]
screeen = display.set_mode(WINSIZE)
display.set_caption('Badminton')
display.set_icon(image.load_extended('assets/badminton-icon.png'))

init()
load()

clock.tick()

done = False
while not done:
    clock.tick(60)
    update()
    draw()