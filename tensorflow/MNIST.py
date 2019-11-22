from __future__ import absolute_import, division, print_function, unicode_literals

import tensorflow.compat.v1 as tf
tf.disable_v2_behavior()
import input_data
import argparse
import matplotlib.pyplot
import cv2

def prepare():
    filepath = "C:/Users/teaisawesome/Pictures/mnist/nulla.png";
    IMG_SIZE = 28
    img = cv2.imread(filepath, cv2.IMREAD_GRAYSCALE)
    img = cv2.resize(img, (IMG_SIZE, IMG_SIZE))
    img = img.astype('float32')
    img = img.reshape(28*28)
    img = 255-img
    img /= 255
    return img

# Create the model
def main():
  mnist = input_data.read_data_sets("MNIST_data/", one_hot=True)
  x = tf.placeholder(tf.float32, [None, 784])
  W = tf.Variable(tf.zeros([784, 10]))
  b = tf.Variable(tf.zeros([10]))
  y = tf.matmul(x, W) + b
    
  y_ = tf.placeholder(tf.float32, [None, 10])
  cross_entropy = tf.reduce_mean(tf.nn.softmax_cross_entropy_with_logits(logits=y,labels=y_))
  train_step = tf.train.GradientDescentOptimizer(0.5).minimize(cross_entropy)

  sess = tf.InteractiveSession()
  # Train
  tf.initialize_all_variables().run()
    
  print("-- A halozat tanitasa")  
  for i in range(1000):
    batch_xs, batch_ys = mnist.train.next_batch(100)
    sess.run(train_step, feed_dict={x: batch_xs, y_: batch_ys})
    if i % 100 == 0:
      print(i/10, "%")
  print("----------------------------------------------------------")
  
  # Test trained model
  print("-- A halozat tesztelese")  
  correct_prediction = tf.equal(tf.argmax(y, 1), tf.argmax(y_, 1))
  accuracy = tf.reduce_mean(tf.cast(correct_prediction, tf.float32))  
  print("-- Pontossag: ", sess.run(accuracy, feed_dict={x: mnist.test.images,
                                      y_: mnist.test.labels}))
  print("----------------------------------------------------------")
  print("-- A MNIST 42. tesztkepenek felismerese, mutatom a szamot:")
  
  img = mnist.test.images[42]
  image = img


  matplotlib.pyplot.imshow(image.reshape(28, 28), cmap=matplotlib.pyplot.cm.binary)
  matplotlib.pyplot.savefig("4.png")  
  matplotlib.pyplot.show()

  classification = sess.run(tf.argmax(y, 1), feed_dict={x: [image]})

  print("-- Ezt a halozat ennek ismeri fel: ", classification[0])
  print("----------------------------------------------------------")
  
  print("-- A sajat kezi 0-asom felismerese, mutatom a szamot:")

  img = prepare()
  image = img

  matplotlib.pyplot.imshow(image.reshape(28,28), cmap=matplotlib.pyplot.cm.binary)
  #matplotlib.pyplot.savefig("8.png")  
  matplotlib.pyplot.show()

  classification = sess.run(tf.argmax(y, 1), feed_dict={x: [image]})

  print("-- Ezt a halozat ennek ismeri fel: ", classification[0])
  print("----------------------------------------------------------")

if __name__ == "__main__":
    main()
