from keras.datasets import cifar10
from keras.utils import np_utils
from keras.models import Sequential
from keras.layers.core import Dense, Dropout, Activation, Flatten
from keras.layers.convolutional import Conv2D, MaxPooling2D
from keras.optimizers import SGD, Adam, RMSprop
import matplotlib.pyplot as plt

# CIFAR10 is a set of 60k images 32x32 piels on 3 channels
IMG_CHANNELS = 3
IMG_ROWS = 32
IMG_COLS = 32

#constant
BATCH_SIZE = 128
NB_EPOCH = 20
NB_CLASSES = 10
VERBOSE = 1
VALIDATION_SPLIT = 0.2
OPTIM = RMSprop()

#Load dataset
(X_train, y_train), (X_test, y_test) = cifar10.load_data()
print('X_train shape:', X_train.shape)
print(X_train.shape[0], 'train samples')
print(X_test.shape[0], 'test samples')

# convert to categorical
Y_train = np_utils.to_categorical(y_train, NB_CLASSES)
Y_test = np_utils.to_categorical(y_test, NB_CLASSES)

# float and normalization
X_train = X_train.astype('float32')
X_test = X_test.astype('float32')
X_train /= 255
X_test /= 255

# network
model = Sequential()
model.add(Conv2D(32, (3, 3), padding='same', input_shape=(IMG_ROWS, IMG_COLS, IMG_CHANNELS)))
model.add(Activation('relu'))
model.add(MaxPooling2D(pool_size=(2, 2)))
model.add(Dropout(0.25))
model.add(Flatten())
model.add(Dense(512))
model.add(Activation('relu'))
model.add(Dropout(0.5))
model.add(Dense(NB_CLASSES))
model.add(Activation('softmax'))
model.summary()

# train
model.compile(loss='categorical_crossentropy', optimizer=OPTIM, metrics=['accuracy'])
model.fit(X_train, Y_train, batch_size=BATCH_SIZE, epochs=NB_EPOCH, validation_split=VALIDATION_SPLIT, verbose=VERBOSE)
score = model.evaluate(X_test, Y_test, batch_size=BATCH_SIZE, verbose=VERBOSE)
print("Test score:", score[0])
print("Test accuracy:", score[1])

# save model
model.save("cifar10_saved_model.h5")

# save weights
model.save_weights("cifar10_saved_weights.h5")

model.summary()

from PIL import Image
import numpy as np

classes = ["airplane", "automobile", "bird", "cat", "deer", "dog", "frog", "horse", "ship", "truck"]

img = Image.open("automobile.jpg")
img = img.resize((32, 32))
image = np.array(img)
result = model.predict_classes(image.reshape((1, 32, 32, 3)))
print(" ---------------------------------------------------")
print("Kép: Bátfai Tanár Úr legendás matchboxa.")
print( model.predict_proba(image.reshape(1, 32, 32, 3)) )
print( classes[result[0]] )

# [[0. 1. 0. 0. 0. 0. 0. 0. 0. 0.]]
# automobile

img = Image.open("deer.png")
img = img.resize((32, 32))
image = np.array(img)
result = model.predict_classes(image.reshape((1, 32, 32, 3)))
print(" ---------------------------------------------------")
print("Kép: Szarvas")
print( model.predict_proba(image.reshape(1, 32, 32, 3)) )
print( classes[result[0]] )

# [[0. 0. 0. 0. 1. 0. 0. 0. 0. 0.]]
# deer

img = Image.open("IFA.jpg")
img = img.resize((32, 32))
image = np.array(img)
result = model.predict_classes(image.reshape((1, 32, 32, 3)))
print(" ---------------------------------------------------")
print("Kép: Saját választott képem egy IFA-ról.")
print( model.predict_proba(image.reshape(1, 32, 32, 3)) )
print( classes[result[0]] )

# [[0. 0. 0. 0. 0. 0. 0. 0. 0. 1.]]
# truck
