Java面试题集

## ⭐架构师⭐

### 1.大型网站在架构上应当考虑哪些问题

**分层** 

- 分层是处理任何复杂系统最常见的手段之一，将系统横向切分成若干个层面，每个层面只承担单一的
  职责，然后通过下层为上层提供的基础设施和服务以及上层对下层的调用来形成一个完整的复杂的系统。计
  算机网络的开放系统互联参考模型（OSI/RM）和Internet的TCP/IP模型都是分层结构，大型网站的软件系统
  也可以使用分层的理念将其分为持久层（提供数据存储和访问服务）、业务层（处理业务逻辑，系统中最核
  心的部分）和表示层（系统交互、视图展示）。需要指出的是：（1）分层是逻辑上的划分，在物理上可以位
  于同一设备上也可以在不同的设备上部署不同的功能模块，这样可以使用更多的计算资源来应对用户的并发
  访问；（2）层与层之间应当有清晰的边界，这样分层才有意义，才更利于软件的开发和维护。

**分割**

- 分割是对软件的纵向切分。我们可以将大型网站的不同功能和服务分割开，形成高内聚低耦合的功能
  模块（单元）。在设计初期可以做一个粗粒度的分割，将网站分割为若干个功能模块，后期还可以进一步对
  每个模块进行细粒度的分割，这样一方面有助于软件的开发和维护，另一方面有助于分布式的部署，提供网
  站的并发处理能力和功能的扩展。

**分布式**

- 除了上面提到的内容，网站的静态资源（JavaScript、CSS、图片等）也可以采用独立分布式部署并
  采用独立的域名，这样可以减轻应用服务器的负载压力，也使得浏览器对资源的加载更快。数据的存取也应
  该是分布式的，传统的商业级关系型数据库产品基本上都支持分布式部署，而新生的NoSQL产品几乎都是分
  布式的。当然，网站后台的业务处理也要使用分布式技术，例如查询索引的构建、数据分析等，这些业务计
  算规模庞大，可以使用Hadoop以及MapReduce分布式计算框架来处理。

**集群**

- 集群使得有更多的服务器提供相同的服务，可以更好的提供对并发的支持。

**缓存**

- 所谓缓存就是用空间换取时间的技术，将数据尽可能放在距离计算最近的位置。使用缓存是网站优化的第一定律。我们通常说的CDN、反向代理、热点数据都是对缓存技术的使用。

**异步**

- 异步是实现软件实体之间解耦合的又一重要手段。异步架构是典型的生产者消费者模式，二者之间没
  有直接的调用关系，只要保持数据结构不变，彼此功能实现可以随意变化而不互相影响，这对网站的扩展非
  常有利。使用异步处理还可以提高系统可用性，加快网站的响应速度（用Ajax加载数据就是一种异步技
  术），同时还可以起到削峰作用（应对瞬时高并发）。“能推迟处理的都要推迟处理”是网站优化的第二定
  律，而异步是践行网站优化第二定律的重要手段

**冗余**

- 各种服务器都要提供相应的冗余服务器以便在某台或某些服务器宕机时还能保证网站可以正常工作，
  同时也提供了灾难恢复的可能性。冗余是网站高可用性的重要保证。

### 2.网站前端优化的技术有哪些？

- 浏览器访问优化

  - 减少HTTP请求数量：合并CSS、合并JavaScript、合并图片（CSS Sprite）

  - 使用浏览器缓存：通过设置HTTP响应头中的Cache-Control和Expires属性，将CSS、JavaScript、图片等在浏览器中缓存，当这些静态资源需要更新时，可以更新HTML文件中的引用来让浏览器重新请求新的资源
  - 启用压缩
  - CSS前置，JavaScript后置
  - 减少Cookie传输

- CDN加速：
  - CDN（Content Distribute Network）的本质仍然是缓存，将数据缓存在离用户最近的地方，CDN通常部署在网络运营商的机房，不仅可以提升响应速度，还可以减少应用服务器的压力。当然，CDN缓存的通常都是静态资源。

- 反向代理：
  - 反向代理相当于应用服务器的一个门面，可以保护网站的安全性，也可以实现负载均衡的功能，当然最重要的是它缓存了用户访问的热点资源，可以直接从反向代理将某些内容返回给用户浏览器。

### 3.应用服务器优化技术有哪些？

1. **分布式缓存**  分布式缓存的本质就是内存中的哈希表，缓存主要用来存储那些读写比较高，变化很少的数据，这样应用从缓存中读取大大提高了访问效率。网站的数据访问符合二八定律，即80% 的访问都集中在20% 的数据上，如果能将这20% 的数据缓存起来，那么系统的性能将得到显著的改善。 

   **使用缓存的问题：**

   - 频繁修改的数据、数据不一致与脏读、缓存雪崩（可以采用分布式集群得以解决）、缓存预热、缓存穿透（可以采用布隆过滤算法解决）

2. **异步操作** 可以使用消息队列将调用异步化，通过异步化处理将短时间高并发产生的事件存储到消息队列中，从而起到削峰的作用。

3. **使用集群 ** 

4. **代码优化**

   **多线程** 解决线程安全问题，只要考虑以下方面

   - 在方法内部创建对象，这样对象进入方法的线程创建，不会出现多线程同时访问一个对象问题。使用ThreadLocal 将对象与线程绑定也是很好的做法
   - 资源在并发访问时使用合理的锁机制

   **非阻塞I/O** 使用单线程和非阻塞I/O 是目前工人的比多线程的方式更能充分发挥服务器性能的应用模式，基于Node.js 构建的服务器就采用了这样的方式

   **资源复用** 

   - 资源复用有两种方式 一是单例，而是对象池，我们使用的数据库连接池、线程池都
     是对象池化技术，这是典型的用空间换取时间的策略
   - 另一方面也实现对资源的复用，从而避免了不必要的创建和释放资源所带来的开销。

## ⭐容器⭐

### **1.java 容器都有哪些？**

![image-20201003214954049](C:\Users\hp\Desktop\java面试题\img\image-20201003214954049.png)

### **2.Collection 和 Collections 有什么区别？**

- java.util.Collection 是一个集合接口（集合类的一个顶级接口）。它提供了对集合对象进行基本操作的通用接口方法。
  - Collection接口在Java 类库中有很多具体的实现。
  - Collection接口的意义是为各种具体的集合提供了最大化的统一操作方式，其直接继承接口有List与Set。
- Collections则是集合类的一个**工具类/帮助类**，其中提供了一系列静态方法，用于对集合中元素进行**排序、搜索以及线程安全等各种操作**。

### **3.List、Set、Map 之间的区别是什么？**

![image-20201003215225756](C:\Users\hp\Desktop\java面试题\img\image-20201003215225756.png)

### **4.HashMap 和 Hashtable 有什么区别？**

- hashMap去掉了HashTable 的contains方法，但是加上了containsValue（）和containsKey（）方法。
- hashTable同步的，而HashMap是非同步的，效率上逼hashTable要高。
- hashMap允许空键值，而hashTable不允许。 

### **5.如何决定使用 HashMap 还是 TreeMap？**

- 对于在Map中插入、删除和定位元素这类操作，HashMap是最好的选择。
- 然而，假如你需要对一个**有序的key集合进行遍历**，TreeMap是更好的选择。

基于你的collection的大小，也许向HashMap中添加元素会更快，将map换为TreeMap进行有序key的遍历。

### **6.说一下 HashMap 的实现原理？**

HashMap概述：HashMap是基于哈希表的Map接口的非同步实现。此实现提供所有可选的映射操作，并允许使用null值和null键。此类不保证映射的顺序，特别是它不保证该顺序恒久不变。 

**HashMap是一个“链表散列”的数据结构，即数组和链表的结合体。**

**JDK7**

- 当我们往Hashmap中put元素时,首先根据key的hashcode重新计算hash值,根绝hash值得到这个元素在数组中的位置(下标),如果该数组在该位置上已经存放了其他元素,那么在这个位置上的元素将以链表的形式存放,**新加入的放在链头**,**最先加入的放入链尾**.如果数组中该位置没有元素,就直接将该元素放到数组的该位置上。

- 采用数组加链表实现

  ![image-20201003220300479](C:\Users\hp\Desktop\java面试题\img\image-20201003220300479.png)

**JDK8**

- 需要注意Jdk 1.8中对HashMap的实现做了优化,当链表中的节点数据超过八个之后,该链表会转为红黑树来提高查询效率,从原来的O(n)到O(logn)

- 采用数组加+链表+红黑树实现

  ![image-20201003220358050](C:\Users\hp\Desktop\java面试题\img\image-20201003220358050.png)

### 7.为什么HashMap线程不安全？

- 同时put碰撞导致数据丢失
- 同时put 扩容导致数据丢失
- 在多线程同时扩容的时候会造成链表的互相指向，导致死循环
- 死循环造成CPU 100% 在JDK7 之前存在

### 8.说一下ConcurrentHashMap 的实现原理

**JDK7**

- 最外层是多个segment，每个segment的底层数据结构是,与HashMap类似，仍然是数组＋链表组成的拉链法
- 每个segment独立上ReentrantLock 锁，每个segment之间互不影响，提高了并发效率
- ConcurrenHashMap默认有16个segments，所以最多可支持16个线程并发写（操作分别分布在不同segment上）这个默认值可以在初始化的时候设置为其他值，但是一旦初始化后，是不可以扩容的。

![image-20201003220629079](C:\Users\hp\Desktop\java面试题\img\image-20201003220629079.png)

**JDK8**

- Java 1.8 采用CAS 加 synchronized 实现

![image-20201003221240352](C:\Users\hp\Desktop\java面试题\img\image-20201003221240352.png)

### 9. JDK1.7与JDK1.8的不同

**数据结构不同**
	•  Java 1.7 中采用cegment 结构，默认只有16个，并发度低
	• Java 1.8 中采用链表加红黑树结构，提高了并发性

**Hash碰撞**
	• Java 1.7 中采用拉链法，时间复杂度是O(n)
	• Java 1.8 中采用链表加红黑树算法，时间复杂度是O(logn)

**保证并发安全不同**
Java 1.7 采用的cegement 分段锁 继承自 ReentrantLock 可重入锁
Java 1.8 采用CAS 加 synchronized 实现

### **10.说一下 HashSet 的实现原理？**

- HashSet底层由HashMap实现
- HashSet的值存放于HashMap的key上
- HashMap的value统一为PRESENT

### **11.ArrayList 和 LinkedList 的区别是什么？**

- 最明显的区别是 ArrrayList底层的数据结构是数组，支持随机访问，
- 而 LinkedList 的底层数据结构是双向循环链表，不支持随机访问。

使用下标访问一个元素，ArrayList 的时间复杂度是 O(1)，而 LinkedList 是 O(n)。

### **12.如何实现数组和 List 之间的转换？**

- List转换成为数组：调用ArrayList的toArray方法。
- 数组转换成为List：调用Arrays的asList方法。

### **13.ArrayList 和 Vector 的区别是什么？**

- Vector是同步的，而ArrayList不是。然而，**如果你寻求在迭代的时候对列表进行改变**，你应该使用CopyOnWriteArrayList。 
- ArrayList比Vector快，它因为有同步，不会过载。 
- ArrayList更加通用，因为我们可以使用Collections工具类轻易地获取同步列表和只读列表。

### **14.Array 和 ArrayList 有何区别？**

- Array可以容纳**基本类型和对象**，而ArrayList只能容纳对象。 
- Array是指定大小的，而ArrayList大小是固定的。 
- Array没有提供ArrayList那么多功能，比如addAll、removeAll和iterator等。

### **15.在 Queue 中 poll()和 remove()有什么区别？**

poll() 和 remove() 都是从队列中取出一个元素，

**1. poll() 在获取元素失败的时候会返回空**

![image-20201003222127060](C:\Users\hp\Desktop\java面试题\img\image-20201003222127060.png)

**2. 但是 remove() 失败的时候会抛出异常。**

![image-20201003222047381](C:\Users\hp\Desktop\java面试题\img\image-20201003222047381.png)

### **16.哪些集合类是线程安全的？**

- vector：就比arraylist多了个同步化机制（线程安全），因为效率较低，现在已经不太建议使用。在web应用中，特别是前台页面，往往效率（页面响应速度）是优先考虑的。
- statck：堆栈类，先进后出。
- hashtable：就比hashmap多了个线程安全。
- enumeration：枚举，相当于迭代器。

### **17迭代器 Iterator 是什么？**

迭代器是一种设计模式，它是一个对象，它可以遍历并选择序列中的对象，而开发人员不需要了解该序列的底层结构。迭代器通常被称为“轻量级”对象，因为创建它的代价小。

###  **18.Iterator 怎么使用？有什么特点？**

**Java中的Iterator功能比较简单，并且只能单向移动：**　　

(1) 使用方法iterator()要求容器返回一个Iterator。第一次调用Iterator的next()方法时，它返回序列的第一个元素。注意：iterator()方法是java.lang.Iterable接口,被Collection继承。

(2) 使用next()获得序列中的下一个元素。　　

(3) 使用hasNext()检查序列中是否还有元素。　

(4) 使用remove()将迭代器新返回的元素删除。　

Iterator是Java迭代器最简单的实现，为List设计的ListIterator具有更多的功能，它可以从两个方向遍历List，也可以从List中插入和删除元素。

### **19.Iterator 和 ListIterator 有什么区别？**

- Iterator可用来遍历Set和List集合，但是ListIterator只能用来遍历List。 
- Iterator对集合只能是前向遍历，ListIterator既可以前向也可以后向。 
- ListIterator实现了Iterator接口，并包含其他的功能，比如：增加元素，替换元素，获取前一个和后一个元素的索引，等等。

### 20.Set用什么方法来区分重复与否呢? 

- Set里的元素是不能重复的，那么用iterator()方法来区分重复与否。

- equals()是判读两个Set是否相等。 

- equals()和==方法决定引用值是否指向同一对象equals()在类中被覆盖，为的是当两个分离的对象的内容和类型相配的话，返回真值。 


## ⭐java基础⭐

#### 1.面向对象的四大特性

##### 封装 

- 隐藏对象的属性及实现细节，仅对外提供公共的接口，将变化隔离，便于使用，提高安全性和复用性

##### 继承

- 提供的代码的复用性，继承是多态的前提

##### 多态

- 父类或接口定义的引用变量可以指向父类或实现类的具体对象。提高了程序的扩展性

##### 抽象

- 将一类对象的共同特征总结出来构造类的过程，包括数据抽象和行为抽象两方面，抽象只关注对象有哪些属相和行为，并不关注行为的细节是什么

### instanceof关键字的作用

instanceof 严格来说是Java中的一个双目运算符，用来测试一个对象是否为一个类的实例，用法为：

```
boolean result = obj instanceof Class
```

### 2. 什么是拆装箱？

```java
public class Test4  {
    public static void main(String[] args) {
        Integer a=127;
        Integer b=127;
        //-128 -- +127 之间的都是相等的
        System.out.println(a==b); //true
        Integer c=129;
        Integer d=129;
        System.out.println(c==d); //false
    }
}
```



### 3. 重载重写 

**overload**（**重载**）

- 发生在同一个类中

- 方法名必须相同 ，参数列表必须不同

- 与访问修饰符、返回值类型无关

- 构造器可以被重载

  **重载的作用：**实现方法名的复用

 **override（重写）**

- 发生在子类中
-  方法名、参数列表、返回值类型必须相同
- ` 访问修饰符不能低于父类的`

### 4. final、finally、finalize 有什么区别？

**final**

- final可以修饰类、变量、方法
- 修饰类表示该类不能被继承、修饰方法表示该方法不能被重写、修饰变量表示该变量是一个常量不能被重新赋值。如果变量是对象则对象的引用不能变，其值可以改变

**finally**

- finally一般作用在try-catch代码块中，在处理异常的时候，通常我们将一定要执行的代码方法finally代码块中，表示不管是否出现异常，该代码块都会执行，一般用来存放一些关闭资源的代码。

**finalize**

- finalize是一个方法，属于Object类的一个方法，而Object类是所有类的父类，该方法一般由垃圾回收器来调用，当我们调用System的gc()方法的时候，由垃圾回收器调用finalize(),回收垃圾。 

- 一旦垃圾回收器**准备好释放对象占用的空间**，**将首先调用其finalize() 方法**，并且在下一次垃圾回收动作发生时，才会真正回收对象占用的内存。



### 5. static

### **6. 什么是 java 序列化？什么情况下需要序列化？**

简单说就是为了保存在内存中的各种对象的状态（也就是实例变量，不是方法），并且可以把保存的对象状态再读出来。虽然你可以用你自己的各种各样的方法来保存object states，但是Java给你提供一种应该比你自己好的保存对象状态的机制，那就是序列化。

**什么情况下需要序列化：?**

a）当你想把的**内存中的对象状态**保存到一个文件中或者数据库中时候；
b）当你想用**套接字在网络上传送对象的时候；**
c）当你想通过RMI传输对象的时候；

### 7.Error与Exception有什么区别？ 

Error表示 **系统级** 的错误和程序不必处理的异常，比如说内存溢出 

Exception 表示需要捕捉或者需要程序进行处理的异常。 

### 8. heap和stack有什么区别。 

栈是一种线形集合，其添加和删除元素的操作应在同一段完成。

栈按照后进先出的方式进行处理。



### 10. String s = new String("xyz");创建了几个String Object? 

- 两个对象，一个是“xyx”,
- 一个是指向“xyx”的引用对象s。

### **11.String 类的常用方法都有那些？**

- indexOf()：返回指定字符的索引。
- charAt()：返回指定索引处的字符。
- replace()：字符串替换。
- trim()：去除字符串两端空白。
- split()：分割字符串，返回一个分割后的字符串数组。
- getBytes()：返回字符串的 byte 类型数组。
- length()：返回字符串长度。
- toLowerCase()：将字符串转成小写字母。
- toUpperCase()：将字符串转成大写字符。
- substring()：截取字符串。
- equals()：字符串比较。

### 12.java 中的 Math.round(-1.5) 等于多少？

等于 -1，因为在数轴上取值时，中间值（0.5）向右取整，所以正 0.5 是往上取整，负 0.5 是直接舍弃。

### 13.short s1 = 1; s1 = s1 + 1;有什么错? short s1 = 1; s1 += 1;有什么错? 

s1是short型，s1+1是int型,不能显式转化为short型。

short s1 = 1; s1 += 1正确。  



### **内部类**（Inner class）

每个内部类都能独立地继承一个（接口的）实现，所以无论外围类是否已经继承了某个（接口的）实现，对于内部类都没有影响

**使用内部类最大的优点就在于它能够非常好的解决多重继承的问题,使用内部类还能够为我们带来如下特性:**
　　(1)、内部类可以用多个实例，每个实例都有自己的状态信息，并且与其他外围对象的信息相互独。
　　(2)、在单个外围类中，可以让多个内部类以不同的方式实现同一个接口，或者继承同一个类。
　　(3)、创建内部类对象的时刻并不依赖于外围类对象的创建。
　　(4)、内部类并没有令人迷惑的“is-a”关系，他就是一个独立的实体。
　　(5)、内部类提供了更好的封装，除了该外围类，其他类都不能访问。

- 用static修饰一个内部类时（嵌套类），这个类相当于是一个外部定义的类，所以static的内部类中可声明static成员
- static内部类不能使用外部类的非static成员变量

### **14.接口和抽象类有什么区别？**

- 实现：抽象类的子类使用 extends 来继承；接口必须使用 implements 来实现接口。
- 构造函数：抽象类可以有构造函数；接口不能有。
- main 方法：抽象类可以有 main 方法，并且我们能运行它；接口不能有 main 方法。
- 实现数量：类可以实现很多个接口；但是只能继承一个抽象类。
- 访问修饰符：接口中的方法默认使用 public 修饰；抽象类中的方法可以是任意访问修饰符。

### **15.java 中 IO 流分为几种？**

按功能来分：输入流（input）、输出流（output）。

按类型来分：字节流和字符流。

**字节流和字符流的区别是：**字节流按 8 位传输以字节为单位输入输出数据，字符流按 16 位传输以字符为单位输入输出数据。

### **16.BIO、NIO、AIO 有什么区别？**

- BIO：Block IO 同步阻塞式 IO，就是我们平常使用的传统 IO，它的特点是模式简单使用方便，并发处理能力低。
- NIO：New IO 同步非阻塞 IO，是传统 IO 的升级，客户端和服务器端通过 Channel（通道）通讯，实现了多路复用。
- AIO：Asynchronous IO 是 NIO 的升级，也叫 NIO2，实现了异步非堵塞 IO ，异步 IO 的操作基于事件和回调机制。

### **17.Files的常用方法都有哪些？**

- Files.exists()：检测文件路径是否存在。
- Files.createFile()：创建文件。
- Files.createDirectory()：创建文件夹。
- Files.delete()：删除一个文件或目录。
- Files.copy()：复制文件。
- Files.move()：移动文件。
- Files.size()：查看文件个数。
- Files.read()：读取文件。
- Files.write()：写入文件。

### 18.try {}里有一个return语句，那么紧跟在这个try后的finally {}里的code会不会被执行，什么时候被执行，在return前还是后? 

　　会执行，在return前执行。 

### 19.编程题: 用最有效率的方法算出2乘以8等于几?  

　　2 << 3 

### 20.char型变量中能不能存贮一个中文汉字?为什么? 

答：是能够定义成为一个中文的，因为java中以unicode编码，一个char占16个字节，所以放一个中文是没问题的  

### 21.值传递

**当一个对象被当作参数传递到一个方法后，此方法可改变这个对象的属性，并可返回变化后的结果，那么这里到底是值传递还是引用传递?** 

- 是值传递。**Java 编程语言只由值传递参数。**
- 当一个对象实例作为一个参数被传递到方法中时，参数的值就是对该对象的引用。对象的内容可以在被调用的方法中改变，**但对象的引用是永远不会改变的**。 



### 22.运行时异常与一般异常有何异同？ 

异常表示程序运行过程中可能出现的非正常状态，运行时异常表示虚拟机的通常操作中可能遇到的异常，是一种常见运行错误。

java编译器要求方法必须声明抛出可能发生的非运行时异常，但是并不要求必须声明抛出未被捕获的运行时异常。 

### 23.String 、StringBuffer和 StringBuilder的区别

- String 和 StringBuffer、StringBuilder 的区别在于 String 声明的是不可变的对象，每次操作都会生成新的 String 对象，然后将指针指向新的 String 对象，
- 而 StringBuffer、StringBuilder 可以在原有对象的基础上进行操作，所以在经常改变字符串内容的情况下最好不要使用 String。
- StringBuffer 和 StringBuilder 最大的区别在于，StringBuffer 是线程安全的，而 StringBuilder 是非线程安全的，但 StringBuilder 的性能却高于 StringBuffer，所以在单线程环境下推荐使用 StringBuilder，多线程环境下推荐使用 StringBuffer。

### 24.” == “和 equals 的区别是什么？

**== 解读**

对于基本类型和引用类型 == 的作用效果是不同的，如下所示：

- 基本类型：比较的是值是否相同；


- 引用类型：比较的是引用是否相同；

  ```
  String x = "string";
  String y = "string";
  String z = new String("string");
  System.out.println(x==y); // true
  System.out.println(x==z); // false
  System.out.println(x.equals(y)); // true
  System.out.println(x.equals(z)); // true
  ```

  代码解读：因为 x 和 y 指向的是同一个引用，所以 == 也是 true，**而 new String()方法则重写开辟了内存空间，**所以 == 结果为 false，而 equals 比较的一直是值，所以结果都为 true。

  **总结** ：

  == 对于基本类型来说是值比较，对于引用类型来说是比较的是引用；

  **而 equals 默认情况下是引用比较**，只是很多类重写了 equals 方法，比如 String、Integer 等把它变成了值比较，所以一般情况下 equals 比较的是值是否相等。

### 25.Equals、hashcode

**1.  两个对象值相同(x.equals(y) == true)，但却可有不同的hash code，这句话对不对?** 

- 两个对象相同 hashcode 一定相同
- hashcode相同，两个对象不一定相同

### 26.什么是反射？

反射主要是指程序可以访问、检测和修改它本身状态或行为的一种能力

**Java反射：**


在Java运行时环境中，对于任意一个类，能否知道这个类有哪些属性和方法？对于任意一个对象，能否调用它的任意一个方法

**Java反射机制主要提供了以下功能：**

- 在运行时判断任意一个对象所属的类。

- 在运行时构造任意一个类的对象。

- 在运行时判断任意一个类所具有的成员变量和方法。

- 在运行时调用任意一个对象的方法。 

### **27.动态代理是什么？有哪些应用？**

**动态代理：**

当想要给实现了某个接口的类中的方法，加一些额外的处理。比如说加日志，加事务等。可以给这个类创建一个代理，故名思议就是创建一个新的类，这个类不仅包含原来类方法的功能，而且还在原来的基础上添加了额外处理的新类。这个代理类并不是定义好的，是动态生成的。具有解耦意义，灵活，扩展性强。

**动态代理的应用：**

- Spring的AOP
- 加事务
- 加权限
- 加日志

### **28.怎么实现动态代理？**

首先必须定义一个接口，还要有一个InvocationHandler(将实现接口的类的对象传递给它)处理类。再有一个工具类Proxy(习惯性将其称为代理类，因为调用他的newInstance()可以产生代理对象,其实他只是一个产生代理对象的工具类）。利用到InvocationHandler，拼接代理类源码，将其编译生成代理类的二进制码，利用加载器加载，并将其实例化产生代理对象，最后返回。

###  29.**如何实现对象克隆？**

**有两种方式：**

1) 实现Cloneable接口并重写Object类中的clone()方法；

2) 实现Serializable接口，通过对象的序列化和反序列化实现克隆，可以实现真正的深度克隆，代码如下：

```java
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class MyUtil {

    private MyUtil() {
        throw new AssertionError();
    }

    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T clone(T obj) throws Exception {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bout);
        oos.writeObject(obj);

        ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bin);
        return (T) ois.readObject();

        // 说明：调用ByteArrayInputStream或ByteArrayOutputStream对象的close方法没有任何意义
        // 这两个基于内存的流只要垃圾回收器清理对象就能够释放资源，这一点不同于对外部资源（如文件流）的释放
    }
}
```

**下面是测试代码：**

```java
mport java.io.Serializable;

class Person implements Serializable {
    private static final long serialVersionUID = -9102017020286042305L;

    private String name;    // 姓名
    private int age;        // 年龄
    private Car car;        // 座驾

    public Person(String name, int age, Car car) {
        this.name = name;
        this.age = age;
        this.car = car;
    }

    //getter setter、、、
   //toString()
}
```

```java
class Car implements Serializable {
    private static final long serialVersionUID = -5713945027627603702L;

    private String brand;       // 品牌
    private int maxSpeed;       // 最高时速

    public Car(String brand, int maxSpeed) {
        this.brand = brand;
        this.maxSpeed = maxSpeed;
    }

   //getter setter、、、
   //toString()
}
```

```java
class CloneTest {

    public static void main(String[] args) {
        try {
            Person p1 = new Person("郭靖", 33, new Car("Benz", 300));
            Person p2 = MyUtil.clone(p1);   // 深度克隆
            p2.getCar().setBrand("BYD");
            // 修改克隆的Person对象p2关联的汽车对象的品牌属性
            // 原来的Person对象p1关联的汽车不会受到任何影响
            // 因为在克隆Person对象时其关联的汽车对象也被克隆了
            System.out.println(p1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

**注意：**基于**序列化**和**反序列化**`实现的克隆`不仅仅是深度克隆，更重要的是通过泛型限定，可以检查出要克隆的对象是否支持序列化，**这项检查是编译器完成的，不是在运行时抛出异常，**

这种是方案明显优于使用Object类的clone方法克隆对象。**让问题在编译的时候暴露出来总是好过把问题留到运行时。**

###  **30.深拷贝和浅拷贝区别是什么？**

- 浅拷贝只是复制了对象的引用地址，两个对象指向同一个内存地址，所以修改其中任意的值，另一个值都会随之变化，这就是浅拷贝（例：assign()）
- 深拷贝是将对象及值复制过来，两个对象修改其中任意的值另一个值不会改变，这就是深拷贝（例：JSON.parse()和JSON.stringify()，但是此方法无法复制函数类型）

### **31.throw 和 throws 的区别？**

- throws是用来声明一个方法可能抛出的所有异常信息，throws是将异常声明但是不处理，而是将异常往上传，谁调用我就交给谁处理。
- 而throw则是指抛出的一个具体的异常类型。

### 32.try-catch-finally 中哪个部分可以省略？

答：catch 可以省略

**原因：**

更为严格的说法其实是：**try只适合处理运行时异常**，try+catch适合处理运行时异常+普通异常。

也就是说，如果你只用try去处理普通异常却不加以catch处理，编译是通不过的，因为编译器硬性规定，普通异常如果选择捕获，则必须用catch显示声明以便进一步处理。

**而运行时异常在编译时没有如此规定，所以catch可以省略，你加上catch编译器也觉得无可厚非。**

理论上，编译器看任何代码都不顺眼，都觉得可能有潜在的问题，所以你即使对所有代码加上try，代码在运行期时也只不过是在正常运行的基础上加一层皮。

但是你一旦对一段代码加上try，就等于显示地承诺编译器，对这段代码可能抛出的异常进行捕获而非向上抛出处理。

**如果是普通异常，编译器要求必须用catch捕获以便进一步处理；**如果运行时异常，捕获然后丢弃并且+finally扫尾处理，或者加上catch捕获以便进一步处理。


至于加上finally，则是在不管有没捕获异常，都要进行的“扫尾”处理。

### **33. try-catch-finally 中，如果 catch 中 return 了，finally 还会执行吗？**

答：会执行，在 return 前执行。

### **34.常见的异常类有哪些？**

- NullPointerException：当应用程序试图访问空对象时，则抛出该异常。
- SQLException：提供关于数据库访问错误或其他错误信息的异常。
- IndexOutOfBoundsException：指示某排序索引（例如对数组、字符串或向量的排序）超出范围时抛出。 
- NumberFormatException：当应用程序试图将字符串转换成一种数值类型，但该字符串不能转换为适当格式时，抛出该异常。
- FileNotFoundException：当试图打开指定路径名表示的文件失败时，抛出此异常。
- IOException：当发生某种I/O异常时，抛出此异常。此类是失败或中断的I/O操作生成的异常的通用类。
- ClassCastException：当试图将对象强制转换为不是实例的子类时，抛出该异常。
- ArrayStoreException：试图将错误类型的对象存储到一个对象数组时抛出的异常。
- IllegalArgumentException：抛出的异常表明向方法传递了一个不合法或不正确的参数。
- ArithmeticException：当出现异常的运算条件时，抛出此异常。例如，一个整数“除以零”时，抛出此类的一个实例。 
- NegativeArraySizeException：如果应用程序试图创建大小为负的数组，则抛出该异常。
- NoSuchMethodException：无法找到某一特定方法时，抛出该异常。
- SecurityException：由安全管理器抛出的异常，指示存在安全侵犯。
- UnsupportedOperationException：当不支持请求的操作时，抛出该异常。
- RuntimeExceptionRuntimeException：是那些可能在Java虚拟机正常运行期间抛出的异常的超类。

## ⭐JavaWeb⭐

### JSP

------

### 1. jsp 和 servlet 有什么区别？

1. jsp经编译后就变成了Servlet.（JSP的本质就是Servlet，JVM只能识别java的类，不能识别JSP的代码，Web容器将JSP的代码编译成JVM能够识别的java类）
2. jsp更擅长表现于页面显示，servlet更擅长于逻辑控制。
3. Servlet中没有内置对象，**Jsp中的内置对象都是必须通过HttpServletRequest对象，   ** **HttpServletResponse** 对象以及HttpServlet对象得到。
4. Jsp是Servlet的一种简化，使用Jsp只需要完成程序员需要输出到客户端的内容，Jsp中的Java脚本如何镶嵌到一个类中，由Jsp容器完成。而Servlet则是个完整的Java类，这个类的Service方法用于生成对客户端的响应。

### **2.jsp 有哪些内置对象？作用分别是什么？**

**JSP有9个内置对象：**

- request：封装客户端的请求，其中包含来自GET或POST请求的参数；
  - 它包含了有关浏览器请求的信息，并且提供了几个用于获取**cookie, header, 和session**数据的有用的方法。 
- response：封装服务器对客户端的响应；
  - 并提供了几个用于设置**送回** 浏览器的响应的方法（**如cookies,头信息等）** 
- pageContext：通过该对象可以获取其他对象；
  - 它是用于方便存取各种范围的名字空间、servlet相关的对象的API，并且包装了**通用的servlet相关功能的方法。** 
- session：封装用户会话的对象；Session可以存贮用户的状态信息 
- application：封装服务器运行环境的对象； 这有助于查找有关servlet引擎和servlet环境的信息 
- out：输出服务器响应的输出流对象；
- config：Web应用的配置对象；
- page：JSP页面本身（相当于Java程序中的this）；
- exception：封装页面抛出异常的对象。

### **3.说一下 jsp 的 4 种作用域？**

**JSP中的四种作用域包括page、request、session和application，具体来说**：

- **page** 代表与一个页面相关的对象和属性。
- **request** 代表与Web客户机**发出**的一个请求相关的对象和属性。一个请求可能跨越多个页面，涉及多个Web组件；需要在页面显示的临时数据可以置于此作用域。
- **session** 代表与某个**用户与服务器建立的一次会话相关的对象和属性。**跟某个用户相关的数据应该放在用户自己的session中。
- **application **代表与整个Web应用程序相关的对象和属性，它实质上是**跨越整个Web应用程序，包括多个页面、请求和会话的一个全局作用域。**

### **4.session 和 cookie 有什么区别？**

**Session**

- 由于**HTTP协议是无状态的协议**，所以服务端需要记录用户的状态时，就需要用某种机制来识具体的用户，这个机制就是**Session**
- 典型的场景比如**购物车**，当你点击下单按钮时，由于HTTP协议无状态，所以并不知道是哪个用户操作的，所以服务端要为特定的用户创建了特定的Session，**用于标识这个用户，**并且跟踪用户，这样才知道购物车里面有几本书。
- 这个Session是**保存在服务端的，有一个唯一标识**。
- 在**服务端保存Session**的方法很多，**内存、数据库、文件都有**。集群的时候也要考虑Session的转移，在大型的网站，一般会有专门的Session服务器集群，用来保存用户会话，这个时候 Session 信息都是放在内存的，使用一些缓存服务比如**Memcached之类的来放 Session。**

**Cookie**

- **思考一下服务端如何识别特定的客户？**
- 这个时候Cookie就登场了。每次HTTP请求的时候，客户端都会发送相应的Cookie信息到服务端。实际上大多数的应用都是用 Cookie 来实现Session跟踪的，第一次创建Session的时候，服务端会在HTTP协议中告诉客户端，**需要在 Cookie 里面记录一个Session ID，以后每次请求把这个会话ID发送到服务器**，我就知道你是谁了。
- **有人问，如果客户端的浏览器禁用了 Cookie 怎么办？**
  - 一般这种情况下，会使用一种叫做**URL重写的技术**来进行会话跟踪，即每次HTTP交互，URL后面都会被附加上一个诸如 sid=xxxxx 这样的参数，服务端据此来识别用户。

- **Cookie其实还可以用在一些方便用户的场景下，设想你某次登陆过一个网站，下次登录的时候不想再次输入账号了，怎么办？**
  - 这个信息可以写到Cookie里面，访问网站的时候，网站页面的脚本可以读取这个信息，就自动帮你把用户名给填了，能够方便一下用户。这也是Cookie名称的由来，给用户的一点甜头。

**总结**

- Session**是在服务端保存的一个数据结构**，用来跟踪用户的状态，这个数据可以保存在集群、数据库、文件中；
- Cookie是客户端保存用户信息的一种机制，用来记录用户的一些信息，也是实现Session的一种方式。

### **5.说一下 session 的工作原理？**

其实session是一个存在服务器上的类似于一个**散列表格的文件**。

里面存有我们需要的信息，在我们需要用的时候可以从里面取出来。**类似于一个大号的map**

里面的键存储的是**用户的sessionid，**用户向服务器发送请求的时候会带上这个sessionid。这时就可以从中取出对应的值了。

### **6.如果客户端禁止 cookie 能实现 session 还能用吗？**

Cookie与 Session，一般认为是两个独立的东西，

- Session采用的是在服务器端保持状态的方案，
- 而Cookie采用的是在客户端保持状态的方案。

**为什么禁用Cookie就不能得到Session呢？**

- 因为Session是用Session ID来确定当前对话所对应的服务器Session，而Session ID是通过Cookie来传递的，禁用Cookie相当于失去了Session ID，也就得不到Session了。

**假定用户关闭Cookie的情况下使用Session，其实现途径有以下几种：**

1. 设置php.ini配置文件中的“session.use_trans_sid = 1”，或者编译时打开打开了“--enable-trans-sid”选项，让PHP自动跨页传递Session ID。
2. **手动通过URL传值、隐藏表单传递Session ID。**
3. 用文件、数据库等形式保存Session ID，在跨页过程中手动调用。

### **7.spring mvc 和 struts 的区别是什么？**

**1. 拦截机制的不同**

**Struts2**

- 是类级别的拦截，**每次请求就会创建一个Action**，和Spring整合时Struts2的ActionBean注入作用域是**原型模式prototype**，然后通过setter，getter吧request数据注入到属性。
- Struts2中，一个Action对应一个request，response上下文，在接收参数时，可以通过属性接收，这说明属性参数是让多个方法共享的。**Struts2中Action的一个方法可以对应一个url， **  **而其类属性却被所有方法共享**，这也就无法用注解或其他方式标识其所属方法了，只能设计为多例。

**SpringMVC**

- 是**方法**级别的拦截，**一个方法对应一个Request上下文**，所以方法直接基本上是独立的，独享request，response数据。
- 而每个方法同时又何一个url对应，参数的传递是直接注入到方法中的，是方法所独有的。处理结果通过ModeMap返回给框架。在Spring整合时，**SpringMVC的Controller Bean默认单例模式Singleton，所以默认对所有的请求，只会创建一个Controller，**有应为没有共享的属性，所以是线程安全的，如果要改变默认的作用域，需要添加@Scope注解修改。　

Struts2有自己的拦截Interceptor机制，**SpringMVC这是用的是独立的Aop方式**，这样导致Struts2的配置文件量还是比SpringMVC大。

**2.底层框架的不同**

- Struts2采用**Filter**（StrutsPrepareAndExecuteFilter）实现，Filter在容器启动之后即初始化；服务停止以后坠毁，晚于Servlet。
- SpringMVC（DispatcherServlet）则采用**Servlet实现。**Servlet是在调用时初始化，先于Filter调用，服务停止后销毁。

**3.性能方面**

- Struts2是类级别的拦截，每次请求**对应实例一个新的Action**，**需要加载所有的属性值注入，**
- SpringMVC实现了**零配置**，由于SpringMVC**基于方法的拦截**，**有加载一次单例模式bean注入**。**所以，SpringMVC开发效率和性能高于Struts2。**

**4.配置方面**

spring MVC和Spring是无缝的。从这个项目的管理和安全上也比Struts2高。

### servlet 

#### 1、doGet()和doPost()区别？ 

**doGet()**

- 安全性差。因为是直接将数据显示在地址栏中，浏览器有缓冲，可记录用户信息。
- 只能提交256个字符（1024字节）
- 数据传输载体是URL（提交方式能form也能任意URL链接）
- **get方式有四种：**
  - 1）直接在URL地址栏中输入URL。
  - 2）网页中的超链接。
  - 3）form中method为get。
  - 4）form中method为空时，**默认是get提交。**

**doPost()**

- 安全性高。因为post方式提交数据时是采用的HTTP post机制，是将表单中的字段与值放置在HTTP HEADER内一起传送到ACTION所指的URL中，用户是看不见的。
- Post适合发送大量的数据。
- post只知道有一种：form中method属性为post。

通常我们使用的都是**doPost方法**，你只要在servlet中让这两个方法互相调用就行了，例如在doGet方法中这样写

```java
public void doGet(HttpServletRequest request, HttpServletResponse response)
throws ServletException, IOException {
    doPost(request,response);
}
```

#### 2、servlet的生命周期 

1. Servlet 通过调用 init () 方法进行初始化。

   init 方法被设计成只调用一次。它在第一次创建 Servlet 时被调用，在后续每次用户请求时不再调用。

   **Servlet 是单例的**  多个用户同时访问存在线程安全问题 

   **解决办法：**尽量不要在Servlet 中定义成员变量。即使定义了成员变量，也不要对其修改值

2. Servlet 调用 service() 方法来处理客户端的请求。

   每次访问Servlet时，Service方法都会被调用一次。

3. Servlet 通过调用 destroy() 方法终止（结束）。

   destroy() 方法只会被调用一次，在 Servlet 生命周期结束时被调用。destroy() 方法可以让您的 Servlet 关闭数据库连接、停止后台线程、把 Cookie 列表或点击计数器写入到磁盘，并执行其他类似的清理活动。

#### 3、如何现实servlet的单线程模式 

```jsp
<%@ page isThreadSafe="false"%>
```

#### **4.forward 和redirect的区别?**

**forward(转发)**:

- 是服务器请求资源，服务器直接访问目标地址的URL，把那个URl的相应内容读取过来，然后把这些内容在发给浏览器。对于浏览器来说不知道内容从哪来的，因为跳转过程是在服务器实现的。所以它的地址还是原来的地址

**redirect(重定向)**

- 是服务端根据逻辑发送一个状态码，告诉浏览器重新去请求那个地址，所以地址栏显示的是新的URL

`转发是服务器行为，重定向是客户端行为`

**区别：**

1. 数据共享来说 

- forward：转发页面和转发到的页面可以共享request里面的数据
- redirect 不能共享数据

2. 从地址栏来说 forward 不变 redirect 边

3. 从运用地方来说

   forward： 一般用于用户登录的时候，根据角色转发到相应的模块

   redirect：一般用于用户注销登录时返回主页面或跳转到其它的网站等

4. 从效率来说

   forward 高于 direct

#### 5.什么情况下调用doGet()和doPost()？

Jsp页面中的form标签里的method属性为get时调用doGet()，为post时调用doPost()。

#### **6.JSP和Servlet有哪些相同点和不同点，他们之间的联系是什么？** 

 答：JSP是Servlet技术的扩展，本质上是Servlet的简易方式，更强调应用的外表表达。JSP编译后是**"类servlet"**。

Servlet和JSP最主要的不同点在于，Servlet的应用逻辑是在Java文件中，并且完全从表示层中的HTML里分离开来。而JSP的情况是Java和HTML可以组合成一个扩展名为.jsp的文件。

JSP侧重于视图，Servlet主要用于控制逻辑。

#### **7.请解释Filter和Listener的理解及作用？**、



### EJB 

#### EJB与JAVA BEAN的区别？ 


答:EJB与JAVA BEAN是SUN的不同组件规范，EJB是在容器中运行的，分步式的，而JAVA BEAN主要是一种可利用的组件，主要在客户端UI表现上。 

#### 1、EJB容器提供的服务 

主要提供声明周期管理、代码产生、持续性管理、安全、事务管理、锁和并发行管理等服务。 

#### 2、EJB的角色和三个对象 

EJB角色主要包括Bean开发者 应用组装者 部署者 系统管理员 EJB容器提供者 EJB服务器提供者 

三个对象是Remote（Local）接口、Home（LocalHome）接口，Bean类 

#### 2、EJB的几种类型 

会话（Session）Bean ，实体（Entity）Bean 消息驱动的（Message Driven）Bean 

会话Bean又可分为有状态（Stateful）和无状态（Stateless）两种 

实体Bean可分为Bean管理的持续性（BMP）和容器管理的持续性（CMP）两种 

#### 3、bean 实例的生命周期 

对于Stateless Session Bean、Entity Bean、Message Driven Bean一般存在缓冲池管理，而对于Entity Bean和Statefull Session Bean存在Cache管理，通常包含创建实例，设置上下文、创建EJB Object（create）、业务方法调用、remove等过程，对于存在缓冲池管理的Bean，在create之后实例并不从内存清除，而是采用缓冲池调度机制不断重用实例，而对于存在Cache管理的Bean则通过激活和去激活机制保持Bean的状态并限制内存中实例数量。 

#### 4、激活机制 

以Statefull Session Bean 为例：其Cache大小决定了内存中可以同时存在的Bean实例的数量，根据MRU或NRU算法，实例在激活和去激活状态之间迁移，激活机制是当客户端调用某个EJB实例业务方法时，如果对应EJB Object发现自己没有绑定对应的Bean实例则从其去激活Bean存储中（通过序列化机制存储实例）回复（激活）此实例。状态变迁前会调用对应的ejbActive和ejbPassivate方法。 

#### 5、remote接口和home接口主要作用 

remote接口定义了业务方法，用于EJB客户端调用业务方法 

home接口是EJB工厂用于创建和移除查找EJB实例 

#### 6、客服端调用EJB对象的几个基本步骤 

一、 设置JNDI服务工厂以及JNDI服务地址系统属性 

二、 查找Home接口 

三、 从Home接口调用Create方法创建Remote接口 

四、 通过Remote接口调用其业务方法 

## ⭐Spring⭐

### 1.为什么要使用Spring？

使用Spring 框架带来的主要好处

- Dependency Injection(DI) 依赖注入 使得构造器和 JavaBean properties 文件的依赖关系一目了然
- 与 EJB 容器相比较，Ioc 容器更加趋向于轻量级。这样一来IoC容器在有限的内存和CPU 资源的情况下进行应用程序的开发和发布 就变得十分有利
- Spring 并没有闭门造车，Spring 利用了已有的技术 比如ORM框架、logging 框架、 j2EE、
- Spring 框架是按照模块的形式组织的。由包和类的编号就可以看出其所属的模块，开发者仅仅需要选用他们需要的模块即可
- 测试 Spring 开发的应用程序十分简单，利用JavaBean形式的POJO类，可以很方便的利用DI 来写入测试数据
- Spring MVC 是一个精心设计的WEB 框架
- Spring 提供了一个便捷的事务管理接口，使用于小型的本地事务处理

### 2.谈谈你对IoC 和AOP 的理解

**IoC**（inverse of control）控制反转，是一种设计思想，就是将原本程序中手动创建(、初始化、销毁) 的对象交给Spring框架来管理。

IoC就像一个工厂，当我们需要使用一个对象的时候，只需要配置好注解/文件即可，完全不用考虑是如何被创建出来的。

**AOP**:面向切面编程，能够将那些与业务无关却又通用的功能（例如事务处理，日志管理，权限控制等）封装起来，降低代码的耦合性，增加可扩展性和可维护性。

spring AOP 是基于动态代理的，如果要代理的对象实现了接口，那么使用 JDK Proxy,去创建代理对象，对没有实现接口的对象，这时会使用Cglib

![image-20201028221426803](C:\Users\hp\Desktop\java面试题\img\image-20201028221426803.png)

### 3.Spring AOP 和 AspectJ AOP 有什么区别？

Spring AOP 属于运⾏时增强，⽽ AspectJ 是编译时增强。 Spring AOP 基于代理(Proxying)，⽽AspectJ 基于字节码操作(Bytecode Manipulation)。

Spring AOP 已经集成了 AspectJ ，AspectJ 应该算的上是 Java ⽣态系统中最完整的 AOP 框架了。

AspectJ 相⽐于 Spring AOP 功能更加强⼤，但是 Spring AOP 相对来说更简单，
如果我们的切⾯⽐较少，那么两者性能差异不⼤。

但是，当切⾯太多的话，最好选择 AspectJ ，它⽐Spring AOP 快很多。

### **4.spring 常用的注入方式有哪些？**

Spring通过DI（依赖注入）实现IOC（控制反转），常用的注入方式主要有三种：

1. 构造方法注入
2. setter注入
3. 基于注解的注入

### 5.Spring 中的单例 bean 的线程安全问题了解吗？

⼤部分时候我们并没有在系统中使⽤多线程，所以很少有⼈会关注这个问题。

单例 bean 存在线程问题，主要是因为当多个线程操作同⼀个对象的时候，对这个对象的⾮静态成员变量的写操作会存在线程安全问题。

**常⻅的有两种解决办法：**

1. 在Bean对象中尽量避免定义可变的成员变量（不太现实）。
2. 在类中定义⼀个ThreadLocal成员变量，将需要的可变成员变量保存在 ThreadLocal 中（推荐的⼀种⽅式）。

### **6.spring 支持几种 bean 的作用域？**

当通过spring容器创建一个Bean实例时，不仅可以完成Bean实例的实例化，还可以为Bean指定特定的作用域。**Spring支持如下5种作用域：**

- singleton：单例模式，在整个Spring IoC容器中，使用singleton定义的Bean将只有一个实例
- prototype：原型模式，每次通过容器的getBean方法获取prototype定义的Bean时，都将产生一个新的Bean实例
- request：对于每次HTTP请求，使用request定义的Bean都将**产生一个新实例**，即每次HTTP请求将会产生不同的Bean实例。只有在Web应用中使用Spring时，该作用域才有效
- session：对于每次HTTP Session，使用session定义的Bean都将产生一个新实例。同样只有在Web应用中使用Spring时，该作用域才有效
- globalsession：每个全局的HTTP Session，使用session定义的Bean都将产生一个新实例。典型情况下，仅在使用portlet context的时候有效。同样只有在Web应用中使用Spring时，该作用域才有效

其中比较常用的是singleton和prototype两种作用域。**对于singleton作用域的Bean，每次请求该Bean都将获得相同的实例。**容器负责跟踪Bean实例的状态，负责维护Bean实例的生命周期行为；如果一个Bean被设置成prototype作用域，程序每次请求该id的Bean，Spring都会新建一个Bean实例，然后返回给程序。在这种情况下，Spring容器仅仅使用new 关键字创建Bean实例，一旦创建成功，容器不在跟踪实例，也不会维护Bean实例的状态。

如果不指定Bean的作用域，**Spring默认使用singleton作用域**。

Java在创建Java实例时，需要进行内存申请；**销毁实例时，需要完成垃圾回收，这些工作都会导致系统开销的增加。**因此，prototype作用域Bean的创建、销毁代价比较大。**而singleton作用域的Bean实例一旦创建成功，可以重复使用。**因此，除非必要，否则尽量避免将Bean被设置成prototype作用域。

### **7.spring 自动装配 bean 有哪些方式？**

Spring容器负责创建应用程序中的bean同时通过ID来协调这些对象之间的关系。作为开发人员，我们需要告诉Spring要创建哪些bean并且如何将其装配到一起。

**spring中bean装配有两种方式：**

- 隐式的bean发现机制和自动装配
- 在java代码或者XML中进行显示配置

当然这些方式也可以配合使用。

![image-20201024215330979](C:\Users\hp\Desktop\java面试题\img\image-20201024215330979.png)

### **8.spring 事务实现方式有哪些？**

1. 编程式事务管理对基于 POJO 的应用来说是唯一选择。我们需要在代码中调用beginTransaction()、commit()、rollback()等事务管理相关的方法，这就是编程式事务管理。
2. 基于 TransactionProxyFactoryBean 的声明式事务管理
3. 基于 @Transactional 的声明式事务管理
4. 基于 Aspectj AOP 配置事务

###  9.**说一下 spring 的事务隔离？**

事务隔离级别指的是一个事务对数据的修改与另一个并行的事务的隔离程度，当多个事务同时访问相同数据时，如果没有采取必要的隔离机制，就可能发生以下问题：

- 脏读：一个事务读到另一个事务**未提交**的更新数据。
- 幻读：事务A 按照一定条件进行数据读取， 期间事务B 插入了相同搜索条件的新数据，事务A再次按照原先条件进行读取时，发现了事务B 新插入的数据 称为幻读

- 不可重复读：比方说在同一个事务中先后执行两条一模一样的select语句，期间在此次事务中没有执行过任何DDL语句，但先后得到的结果不一致，这就是不可重复读。

![image-20201028225735587](C:\Users\hp\Desktop\java面试题\img\image-20201028225735587.png)

### 10.Spring 事务中哪⼏种事务传播⾏为?

propagation  n. 传播；繁殖；增殖 

![image-20201028230206919](C:\Users\hp\Desktop\java面试题\img\image-20201028230206919.png)

![image-20201028230347970](C:\Users\hp\Desktop\java面试题\img\image-20201028230347970.png)

### 11.@Transactional(rollbackFor = Exception.class)注解了解吗？

我们知道：Exception分为运⾏时异常 RuntimeException和⾮运⾏时异常。

事务管理对于企业应⽤来说是⾄关重要的，即使出现异常情况，它也可以保证数据的⼀致性。

当 @Transactional 注解作⽤于类上时，**该类的所有 public ⽅法将都具有该类型的事务属性**，同时，我们也可以在⽅法级别使⽤该标注来覆盖类级别的定义。

如果类或者⽅法加了这个注解，那么这个类⾥⾯的⽅法抛出异常，就会回滚，数据库⾥⾯的数据也会回滚。

在 @Transactional 注解中如果不配置 rollbackFor 属性,那么事物只会在遇到 **RuntimeException 的时候才会回滚**,加上 rollbackFor=Exception.class ,**可以让事物在遇到⾮运⾏时异常时也回滚。**

###  14.BeanFactory、FactoryBean 和 ApplicationContext 的区别

**BeanFactory 是一个Bean工厂**，使用简单工厂模式，是Spring IoC的顶级接口，可以理解为含有Bean 集合的工厂类，作用是管理Bean，包括实例化、定位、配置对象及建立这些对象的依赖。

BeanFactory 实例化后并不会自动实例化Bean，**只有 当Bean 被使用时才实例化与装配依赖关系，属于延迟加载，适合多例模式**

------

**FactoryBean 是一个工厂 Bean，使用了工厂方法模式**，作用是生产其他Bean 实例，可以通过实现该接口，提供一个工厂方法来自定义实例化Bean逻辑。

**FactoryBean 接口由 BeanFactory 中配置的对象实现**，这些对象本身就是用于创建对象的工厂，如果一个 Bean 实现了这个接口，那么它就是创建对象的工厂 Bean，而不是 Bean 实例本身。

------

**ApplicationConext 是 BeanFactory 的子接口**，扩展了 BeanFactory 的功能，提供了支持国际化的文本消息，**统一的资源文件读取方式**，**事件传播**以**及应用层的特别配置**等。

容器会在初始化时对配置的Bean 进行预实例化，Bean 的依赖注入在容器初始化时就已经完成，属于立即加载，适合单例模式，一般推荐使用。

### 15.Spring 有几种配置方式

1.  基于XML的配置
2. 基于注解的配置
3. 基于java的配置                                                    

### 16.请解释Spring Bean 的生名周期

在一个bean  实例化时，需要执行一系列的初始化操作以达到可用的状态。同样的，当一个bean不在被调用的时需要进行相关的析构操作，并从bean 容器中移除

Spring bean factory 负责管理在spring 容器中被创建的bean 的生命周期。bean 的声明周期由两组回调（call back） 方法组成

1. 初始化之后调用的回调方法
2. 销毁之前调用的回调方法

spring 框架提供了以下4种方式来管理bean 的声明周期事件

1. InitializingBean 和 DisposableBean 回调接口
2. 针对特殊行为的其他Aware接口
3. Bean配置文件中的Custom initial() 方法和 destroy() 方法
4. @PostConstruct 和@PreDestory 注解方式

![image-20201024214613037](C:\Users\hp\Desktop\java面试题\img\image-20201024214613037.png)

### 17.请解释自动装配模式的区别

![image-20201024220342877](C:\Users\hp\Desktop\java面试题\img\image-20201024220342877.png)

### 18.如何开启基于注解的自动装配

![image-20201024220435607](C:\Users\hp\Desktop\java面试题\img\image-20201024220435607.png)

### 19.请举例解释@Required 注解

![image-20201024220736568](C:\Users\hp\Desktop\java面试题\img\image-20201024220736568.png)

![image-20201024220757015](C:\Users\hp\Desktop\java面试题\img\image-20201024220757015.png)

![image-20201024220821223](C:\Users\hp\Desktop\java面试题\img\image-20201024220821223.png)

### 20.@Autowired注解

![image-20201024221038629](C:\Users\hp\Desktop\java面试题\img\image-20201024221038629.png)

![image-20201024221134400](C:\Users\hp\Desktop\java面试题\img\image-20201024221134400.png)

### 21.@Qualifier注解

![image-20201024221241737](C:\Users\hp\Desktop\java面试题\img\image-20201024221241737.png)

![image-20201024221302988](C:\Users\hp\Desktop\java面试题\img\image-20201024221302988.png)

### 22.构造方法注入和设值注入有什么区别？

![image-20201024221628749](C:\Users\hp\Desktop\java面试题\img\image-20201024221628749.png)

### 23.Spring 框架中有哪些不同类型的事件

![image-20201024222737876](C:\Users\hp\Desktop\java面试题\img\image-20201024222737876.png)

![image-20201024222817539](C:\Users\hp\Desktop\java面试题\img\image-20201024222817539.png)

![image-20201024222931354](C:\Users\hp\Desktop\java面试题\img\image-20201024222931354.png)

![image-20201024222952698](C:\Users\hp\Desktop\java面试题\img\image-20201024222952698.png)

### 24.FileSystemResource 和 ClassPathResource 有何区别

![image-20201024223132591](C:\Users\hp\Desktop\java面试题\img\image-20201024223132591.png)

### 25.Spring 框架中都用到了哪些设计模式

![image-20201024223349455](C:\Users\hp\Desktop\java面试题\img\image-20201024223349455.png)

### 26.@Component 和 @Bean 的区别是什么？

1. 作⽤对象不同:  **@Component 注解作⽤于类**，⽽ @Bean 注解作⽤于⽅法。

2.  @Component 通常是通过类路径扫描来⾃动侦测以及⾃动装配到Spring容器中（我们可以使⽤@ComponentScan 注解定义要扫描的路径从中找出标识了需要装配的类⾃动装配到 Spring 的bean 容器中）。

 @Bean 注解通常是我们在标有该注解的⽅法中定义产⽣这个 bean, @Bean 告诉了Spring这是某个类的示例，当我需要⽤它的时候还给我。

3. @Bean 注解⽐  Component 注解的⾃定义性更强，⽽且很多地⽅我们只能通过  @Bean 注解来注册bean。⽐如当我们引⽤第三⽅库中的类需要装配到  Spring 容器时，则只能通过@Bean 来实现。

### 27.Spring 中的 bean ⽣命周期?

![image-20201028222717127](C:\Users\hp\Desktop\java面试题\img\image-20201028222717127.png)

![image-20201028222734581](C:\Users\hp\Desktop\java面试题\img\image-20201028222734581.png)

![image-20201028222831174](C:\Users\hp\Desktop\java面试题\img\image-20201028222831174.png)

## ⭐Spring MVC⭐

![image-20201024224058438](C:\Users\hp\Desktop\java面试题\img\image-20201024224058438.png)

### **2.优点**

![image-20201024224209831](C:\Users\hp\Desktop\java面试题\img\image-20201024224209831.png)

### 3.Spring MVC 工作流程

**Spring MVC运行流程图：**

![image-20201028223142067](C:\Users\hp\Desktop\java面试题\img\image-20201028223142067.png)

**Spring运行流程描述：**

1. 客户端发送请求，直接请求到DispatcherServlet
2. DispatcherServlet 根据请求信息调用 HandlerMapping，解析请求对应的handler
3. 解析对应的 handler (也就是我们平常说的Conroller 控制器)后，开始由 HandlerAdapter 适配器处理
4. HandlerAdapter 根据对应的handler 来调用真正的处理器请求，并处理相应的业务逻辑
5. 处理完业务逻辑后，返回一个ModelAndView 对象 Model 是数据上的对象， view是逻辑上的 view
6. viewResolver 根据view 查找实际的view 
7. DispatcherServlet 会把 model 传给 View（渲染）
8. 把 view 传给浏览器（请求者）



![image-20201024224842475](C:\Users\hp\Desktop\java面试题\img\image-20201024224842475.png)

### 4.Spring MVC 和struts2的区别

![image-20201024224955281](C:\Users\hp\Desktop\java面试题\img\image-20201024224955281.png)

### 5.基础传参

![image-20201024225310874](C:\Users\hp\Desktop\java面试题\img\image-20201024225310874.png)

### 6.重定向 和转发

![image-20201024225426618](C:\Users\hp\Desktop\java面试题\img\image-20201024225426618.png)

### 7.SpringMVC 用什么对象从后台像前台传递数据

![image-20201024225534742](C:\Users\hp\Desktop\java面试题\img\image-20201024225534742.png)

### 8.怎么把ModelMap 里面的数据放入Session里面

![image-20201024225640251](C:\Users\hp\Desktop\java面试题\img\image-20201024225640251.png)

### 9.@ResponseBody注解 与 Ajax

![image-20201024225728888](C:\Users\hp\Desktop\java面试题\img\image-20201024225728888.png)

###  11.spring mvc 有哪些组件？

**Spring MVC的核心组件：**

1. DispatcherServlet：中央控制器，把请求给转发到具体的控制类
2. Controller：具体处理请求的控制器
3. HandlerMapping：映射处理器，负责映射中央处理器转发给controller时的映射策略
4. ModelAndView：服务层返回的数据和视图层的封装类
5. ViewResolver：视图解析器，解析具体的视图
6. Interceptors ：拦截器，负责拦截我们定义的请求然后做处理工作

### 12.@RequestMapping 的作用是什么？

RequestMapping是一个用来处理请求地址映射的注解，可用于类或方法上。**用于类上，表示类中的所有响应请求的方法都是以该地址作为父路径。**

**RequestMapping注解有六个属性，下面我们把她分成三类进行说明。**

1. **value， method：**


   value：指定请求的实际地址，指定的地址可以是URI Template 模式（后面将会说明）；

   method：指定请求的method类型， GET、POST、PUT、DELETE等；

2. **consumes，produces**


   consumes：指定处理请求的提交内容类型（Content-Type），例如application/json, text/html；

   produces：指定返回的内容类型，仅当request请求头中的(Accept)类型中包含该指定类型才返回；

3. **params，headers**


   ​	params： 指定request中必须包含某些参数值是，才让该方法处理。

   ​	headers：指定request中必须包含某些指定的header值，才能让该方法处理请求。

### 13. 如何解决Web页面乱码问题？

```java
<filter>
	<filter-name>encodingFilter</filter-name>
	<filter-class>
		org.springframework.web.filter.CharacterEncodingFilter
	</filter-class>
           <init-param>
                <param-name>encoding</param-name>
                <param-value>UTF-8</param-value>
           </init-param>
            
           <init-param>
                <param-name>forceEncoding</param-name>
                <param-value>true</param-value>
           </init-param>
	</filter>
        
	<filter-mapping>
		<filter-name>encodingFilter</filter-name>
		<url-pattern>*</url-pattern>
	</filter-mapping>

```

### 14.@RestController和 Controller

Controller 返回⼀个⻚⾯单独使⽤  @Controller 不加  @ResponseBody 的话⼀般使⽤在要返回⼀个视图的情况，这种情况属于⽐较传统的**Spring MVC 的应⽤，对应于前后端不分离的情况。**

![image-20201028220120361](C:\Users\hp\Desktop\java面试题\img\image-20201028220120361.png)

@RestController **返回 JSON 或 XML 形式数据**但 @RestController 只返回对象，对象数据直接以 JSON 或 XML 形式写⼊ HTTP 响应(Response)中，这种情况属于 RESTful Web服务，这也是⽬前⽇常开发所接触的最常⽤的情况**（前后端离）。**

![image-20201028220157645](C:\Users\hp\Desktop\java面试题\img\image-20201028220157645.png)

## ⭐Spring boot⭐

### 1.什么是 spring boot？

在Spring框架这个大家族中，产生了很多衍生框架，比如 Spring、SpringMvc框架等，Spring的核心内容在于控制反转(IOC)和依赖注入(DI),所谓控制反转并非是一种技术，而是一种思想，在操作方面是指在spring配置文件中创建<bean>，依赖注入即为由spring容器为应用程序的某个对象提供资源，比如 引用对象、常量数据等。

SpringBoot是一个框架，一种全新的编程规范，他的产生**简化了框架的使用**，所谓**简化是指简化了Spring众多框架中所需的大量且繁琐的配置文件**，所以 SpringBoot是一个服务于框架的框架，服务范围是简化配置文件。

**为什么要用 spring boot？**

- Spring Boot使编码变简单
- Spring Boot使配置变简单
- Spring Boot使部署变简单
- Spring Boot使监控变简单
- Spring的不足

### 2.spring boot 核心配置文件是什么？

Spring Boot提供了两种常用的配置文件：

- properties文件，
- yml文件   yml文件更年轻，也有很多的坑，可谓成也萧何败萧何，yml通过空格来确定层级关系，使配置文件结构跟清晰，但也会因为微不足道的空格而破坏了层级关系。

### 3.Spring boot 中的监视器是什么

actuator  执行机构 

![image-20201025113529961](C:\Users\hp\Desktop\java面试题\img\image-20201025113529961.png)

### 4.如何在自定义端口上运行 Spring boot

![image-20201025163717257](C:\Users\hp\Desktop\java面试题\img\image-20201025163717257.png)

### 5.什么是Spring Profiles？

![image-20201029102851340](C:\Users\hp\Desktop\java面试题\img\image-20201029102851340.png)

![image-20201029103030129](C:\Users\hp\Desktop\java面试题\img\image-20201029103030129.png)



## ⭐Spring Cloud⭐

###  1.**什么是 spring cloud？**

从字面理解，Spring Cloud 就是致力于分布式系统、云服务的框架。

Spring Cloud 是整个 Spring 家族中新的成员，是最近云服务火爆的必然产物。

Spring Cloud 为开发人员提供了快速构建分布式系统中一些常见模式的工具，例如：

- 配置管理

- 服务注册与发现

- 断路器

- 智能路由

- 服务间调用

- 负载均衡

- 微代理

- 控制总线

- 一次性令牌

- 全局锁

- 领导选举

- 分布式会话

- 集群状态

- 分布式消息

使用 Spring Cloud 开发人员可以开箱即用的实现这些模式的服务和应用程序。这些服务可以任何环境下运行，包括分布式环境，也包括开发人员自己的笔记本电脑以及各种托管平台。

### **2.spring cloud 断路器的作用是什么？**

**断路器（Hystrix）可以防止一个应用程序多次试图执行一个操作，即很可能失败，导致整个系统瘫痪**

当某个服务单元发生故障之后，通过断路器的故障监控（类似保险丝），像调用放返回一个错误响应，而不是长时间的等待。这样就不会使得线程因调用故障服务被长时间得不到释放，避免了故障在分布式系统中的蔓延。

### **3.spring cloud 的核心组件有哪些？**

**① 服务发现——Netflix Eureka**

一个RESTful服务，用来定位运行在AWS地区（Region）中的中间层服务。由两个组件组成：Eureka服务器和Eureka客户端。Eureka服务器用作服务注册服务器。Eureka客户端是一个java客户端，用来简化与服务器的交互、作为轮询负载均衡器，并提供服务的故障切换支持。Netflix在其生产环境中使用的是另外的客户端，它提供基于流量、资源利用率以及出错状态的加权负载均衡。

**② 客服端负载均衡——Netflix Ribbon**

Ribbon，主要提供客户侧的软件**负载均衡算法**。Ribbon客户端组件提供一系列完善的配置选项，比如连接超时、重试、重试算法等。Ribbon内置可插拔、可定制的负载均衡组件。

**③ 断路器——Netflix Hystrix**

断路器可以防止一个应用程序多次试图执行一个操作，即很可能失败，允许它继续而不等待故障恢复或者浪费 CPU 周期，而它确定该故障是持久的。**断路器模式也使应用程序能够检测故障是否已经解决。如果问题似乎已经得到纠正，应用程序可以尝试调用操作**。

**④ 服务网关——Netflix Zuul**

类似nginx，反向代理的功能，不过netflix自己增加了一些配合其他组件的特性。

**⑤ 分布式配置——Spring Cloud Config**

这个还是静态的，得配合Spring Cloud Bus实现动态的配置更新。

### 4.服务注册和发现是什么意思？

![image-20201025101211656](C:\Users\hp\Desktop\java面试题\img\image-20201025101211656.png)

### 5.负载均衡的意义是什么

可以改善跨计算机，计算机集群，网络连接，中央处理单元或磁盘驱动等多种计算

负载均衡指在优化资源使用，最大化吞吐量，最小化响应时间，防止任何单一资源的过载

使用多个组件进行负载均衡而不是单个组件可能会通过冗余来提高 高可用性和高可靠性。

### 6.什么是 Hystrix？ 它如何实现容错

Hystrix 是一个延迟和容错库，指在隔离远程系统，服务和第三方的访问点，当出现不可避免的故障时，停止级联故障并在复杂的分布式系统中时下你弹性。

 当服务出现了故障后，就使用我们预先定义的回退方法

![image-20201025103105859](C:\Users\hp\Desktop\java面试题\img\image-20201025103105859.png)

![image-20201025103120861](C:\Users\hp\Desktop\java面试题\img\image-20201025103120861.png)

![image-20201025103135985](C:\Users\hp\Desktop\java面试题\img\image-20201025103135985.png)

![image-20201025103246683](C:\Users\hp\Desktop\java面试题\img\image-20201025103246683.png)

### 7.什么是 Netflix Fegin

![image-20201025103426442](C:\Users\hp\Desktop\java面试题\img\image-20201025103426442.png)

### 8，Feign 的优缺点

**优点**
使用Spring Cloud Feign继承特性的优点很明显，可以将接口的定义从Controller中剥离，同时配合Maven私有仓库就可以轻易地实现接口定义的共享，实现在构建期的接口绑定，从而有效减少服务客户端的绑定配置。这么做虽然可以很方便地实现接口定义和依赖的共享，不用再复制粘贴接口进行绑定，但是这样的做法使用不当的话会带来副作用。

**缺点**
由于接口在构建期间就建立起了依赖，那么接口变动就会对项目构建造成影响，可能服务提供方修改了一个接口定义，那么会直接导致客户端工程的构建失败。所以，如果开发团队通过此方法来实现接口共享的话，建议在开发评审期间严格遵守面向对象的开闭原则，尽可能地做好前后版本的兼容，防止牵一发而动全身的后果，增加团队不必要的维护工作量

### 9.什么是Spring Cloud Bus

![image-20201025103926634](C:\Users\hp\Desktop\java面试题\img\image-20201025103926634.png)

![image-20201025103941140](C:\Users\hp\Desktop\java面试题\img\image-20201025103941140.png)

![image-20201025104004357](C:\Users\hp\Desktop\java面试题\img\image-20201025104004357.png)





















## ⭐Mybatis⭐

### **1. #{}和 ${}的区别是什么？**

- \#{}是预编译处理，${}是字符串替换；
- Mybatis在处理#{}时，会将sql中的#{}替换为?号，调用PreparedStatement的set方法来赋值；
- Mybatis在处理${}时，就是把${}替换成变量的值；
- 使用#{}可以有效的防止SQL注入，提高系统安全性。

### 2.Mybatis分页

#### 1.mybatis 有几种分页方式？

- 数组分页
- sql分页
- **拦截器分页**(物理分页)： 分⻚插件的基本原理是使⽤ Mybatis 提供的插件接⼝，实现⾃定义插件，在插件的拦截⽅法内拦截待执⾏的 sql，然后重写 sql，根据 dialect ⽅⾔，添加对应的物理分⻚语句和物理分⻚参数。
- **RowBounds分页**(逻辑分页)： 针对ResultSet 结果集执行的内存分页，而非物理分页

#### **2.mybatis 逻辑分页和物理分页的区别是什么？**

- 物理分页速度上并不一定快于逻辑分页，逻辑分页速度上也并不一定快于物理分页。
- 物理分页总是优于逻辑分页：没有必要将属于数据库端的压力加诸到应用端来，就算速度上存在优势,然而其它性能上的优点足以弥补这个缺点。

#### 3.简述 Mybatis 的插件运⾏原理，以及如何编写⼀个插件。

答：Mybatis 仅可以编写针对ParameterHandler 、 ResultSetHandler 、 StatementHandler 、 Executor 这 4 种接⼝的插件，**Mybatis 使⽤ JDK 的动态代理**，为需要拦截的接⼝⽣成代理对象以实现接⼝⽅法拦截功能，每当执⾏这 4 种接⼝对象的⽅法时，就会进⼊拦截⽅法，具体就是  InvocationHandler 的invoke() ⽅法，当然，只会拦截那些你指定需要拦截的⽅法。

实现 Mybatis 的 Interceptor 接⼝并复写 intercept() ⽅法，然后在给插件编写注解，指定要拦截哪⼀个接⼝的哪些⽅法即可，记住，别忘了在配置⽂件中配置你编写的插件。

#### 4.mybatis 分页插件的实现原理是什么？

分页插件的基本原理是使用Mybatis提供的插件接口，实现自定义插件，在插件的拦截方法内拦截待执行的sql，然后重写sql，根据dialect方言，添加对应的物理分页语句和物理分页参数

### 3.延迟加载、缓存

#### **1.mybatis 是否支持延迟加载？延迟加载的原理是什么？**

Mybatis**仅支持association关联对象**和**collection关联集合对象**的延迟加载，

association指的就是一对一，collection指的就是一对多查询。在Mybatis配置文件中，可以配置是否启用延迟加载**lazyLoadingEnabled=true|false**。

它的原理是，使用CGLIB创建目标对象的代理对象，当调用目标方法时，进入拦截器方法，比如调用a.getB().getName()，拦截器invoke()方法发现a.getB()是null值，那么就会单独发送事先保存好的查询关联B对象的sql，把B查询上来，然后调用a.setB(b)，于是a的对象b属性就有值了，接着完成a.getB().getName()方法的调用。这就是延迟加载的基本原理。

当然了，不光是Mybatis，几乎所有的包括Hibernate，支持延迟加载的原理都是一样的。

#### **2.说一下 mybatis 的一级缓存和二级缓存？**

一级缓存: 基于 PerpetualCache 的 HashMap 本地缓存，其存储作用域为 Session，当 Session flush 或 close 之后，该 Session 中的所有 Cache 就将清空，默认打开一级缓存。

二级缓存与一级缓存其机制相同，默认也是采用 PerpetualCache，HashMap 存储，不同在于其存储作用域为 Mapper(Namespace)，并且可自定义存储源，**如 Ehcache**。

默认不打开二级缓存，要开启二级缓存，**使用二级缓存属性类需要实现Serializable序列化接口**(可用来保存对象的状态),可在它的映射文件中配置<cache/> ；

对于缓存数据更新机制，当某一个作用域(一级缓存 Session/二级缓存Namespaces)的进行了C/U/D 操作后，默认该作用域下所有 select 中的缓存将被 clear。

### 4.批处理

#### 1.Mybatis 中如何执行批处理

使用BatchExecutor 完成批处理

#### 2.Mybatis 执⾏批量插⼊，能返回数据库主键列表吗？

答：能，JDBC 都能，Mybatis 当然也能。



### **5.mybatis 和 hibernate 的区别有哪些？**

（1）Mybatis和hibernate不同，它不完全是一个ORM框架，因为MyBatis需要程序员自己编写Sql语句。

（2）Mybatis直接编写原生态sql，可以严格控制sql执行性能，灵活度高，非常适合对关系数据模型要求不高的软件开发，因为这类软件需求变化频繁，一但需求变化要求迅速输出成果。但是灵活的前提是mybatis无法做到数据库无关性，如果需要实现支持多种数据库的软件，则需要自定义多套sql映射文件，工作量大。 

（3）Hibernate对象/关系映射能力强，数据库无关性好，对于关系模型要求高的软件，如果用hibernate开发可以节省很多代码，提高效率。 

### 6.Mybatis执行器

#### **1.mybatis 有哪些执行器（Executor）？**

Mybatis有三种基本的执行器（Executor）：

1. **SimpleExecutor**：每执行一次update或select，就开启一个Statement对象，用完立刻关闭Statement对象。
2. **ReuseExecutor**：执行update或select，以sql作为key查找Statement对象，存在就使用，不存在就创建，**用完后，不关闭Statement对象**，而是放置于Map内，供下一次使用。简言之，就是重复使用Statement对象。
3. **BatchExecutor**：执行update（没有select，JDBC批处理不支持select），将所有sql都添加到批处理中（addBatch()），等待**统一执行**（executeBatch()），它缓存了多个Statement对象，每个Statement对象都是addBatch()完毕后，等待逐一执行executeBatch()批处理。与JDBC批处理相同。

#### 2.Mybatis 中如何指定使⽤哪⼀种 Executor 执⾏器？

在 Mybatis 配置⽂件中，可以指定默认的 ExecutorType 执⾏器类型，也可以⼿动给
DefaultSqlSessionFactory 的创建 SqlSession 的⽅法传递 ExecutorType 类型参数。.

### 7.动态SQL

#### Mybatis 动态 sql 是做什么的？都有哪些动态 sql？能简述⼀下动态 sql 的执⾏原理不？

答：Mybatis 动态 sql 可以让我们在 Xml 映射⽂件内，以标签的形式编写动态 sql，完成逻辑判断和动态拼接 sql 的功能，Mybatis 提供了 9 种动态 sql 标签
trim|where|set|foreach|if|choose|when|otherwise|bind 。

其执⾏原理为，使⽤ OGNL 从sql 参数对象中计算表达式的值，根据表达式的值动态拼接 sql，以此来完成动态 sql 的功能。

### 8.Mybatis 是如何将 sql 执⾏结果封装为⽬标对象并返回的？都有哪些映射形式？

第⼀种是使⽤ <resultMap> 标签，逐⼀定义列名和对象属性名之间的映射关系。

第⼆种是使⽤sql 列的别名功能，将列别名书写为对象属性名，⽐如 T_NAME AS NAME，对象属性名⼀般是 name，⼩写，但是列名不区分⼤⼩写，**Mybatis 会忽略列名⼤⼩写**，智能找到与之对应对象属性名，你甚⾄可以写成 T_NAME AS NaMe，Mybatis ⼀样可以正常⼯作。

有了列名与属性名的映射关系后，Mybatis 通过反射创建对象，同时使⽤反射给对象的属性逐⼀赋值并返回，那些找不到映射关系的属性，是⽆法完成赋值的。.

### 9.Mybatis 有几种查询方式？各有什么区别？

#### 1.MyBatis 实现一对一有几种方式

![image-20201025093907631](C:\Users\hp\Desktop\java面试题\img\image-20201025093907631.png)

#### 2.Mybatis 能执⾏⼀对⼀、⼀对多的关联查询吗？都有哪些实现⽅式，以及它们之间的区别。

**能，**Mybatis 不仅可以执⾏⼀对⼀、⼀对多的关联查询，还可以执⾏多对⼀，多对多的关联查询，多对⼀查询，其实就是⼀对⼀查询，只需要把  selectOne() 修改为  selectList() 即可；多对多查询，其实就是⼀对多查询，只需要把  selectOne() 修改为  selectList() 即可。

关联对象查询，有两种实现⽅式，⼀种是单独发送⼀个 sql 去查询关联对象，赋给主对象，然后返回主对象。

另⼀种是使⽤嵌套查询，嵌套查询的含义为使⽤ join 查询，⼀部分列是 A 对象的属性值，另外⼀部分列是关联对象 B 的属性值，好处是只发⼀个 sql 查询，就可以把主对象和其关联对象查出来。

那么问题来了，join 查询出来 100 条记录，如何确定主对象是 5 个，⽽不是 100 个？其去重复的原理是 <resultMap> 标签内的 <id> ⼦标签，指定了唯⼀确定⼀条记录的 id 列，Mybatis 根据列值来完成 100 条记录的去重复功能， <id> 可以有多个，代表了联合主键的语意。

同样主对象的关联对象，也是根据这个原理去重复的，尽管⼀般情况下，只有主对象会有重复记录，关联对象⼀般不会重复。

举例：下⾯ join 查询出来 6 条记录，⼀、⼆列是 Teacher 对象列，第三列为 Student 对象列，
Mybatis 去重复处理后，结果为 1 个⽼师 6 个学⽣，⽽不是 6 个⽼师 6 个学⽣。
t_id t_name s_id
| 1 | teacher | 38 | | 1 | teacher | 39 | | 1 | teacher | 40 | | 1 | teacher | 41 | | 1 |
teacher | 42 | | 1 | teacher | 43 |

### 11.映射文件

#### 1.简述Mybatis 的XML 映射文件和Mybatis 内部数据结构之间的映射关系

答：Mybatis 将所有 Xml 配置信息都封装到 All-In-One 重量级对象 Configuration 内部。

在 Xml映射⽂件中， <parameterMap> 标签会被解析为  ParameterMap 对象，其每个⼦元素会被解析为ParameterMapping 对象。

 <resultMap> 标签会被解析为  ResultMap 对象，其每个⼦元素会被解析为  ResultMapping 对象。

每⼀个 <select>、<insert>、<update>、<delete> 标签均会被解析为  MappedStatement 对象，标签内的 sql 会被解析为 BoundSql 对象。

![image-20201024231828202](C:\Users\hp\Desktop\java面试题\img\image-20201024231828202.png)

![image-20201024231900065](C:\Users\hp\Desktop\java面试题\img\image-20201024231900065.png)

#### 2.不同的Xml 映射文件，id 是否可以重复

不同的 Xml 映射⽂件，如果配置了 namespace，那么 id 可以重复；如果没有配namespace，
那么 id 不能重复；

毕竟 namespace 不是必须的，只是最佳实践⽽已。原因就是 namespace+id 是作为  Map<String, MappedStatement> 的 key 使⽤的，如果没有namespace，就剩下 id，那么，id 重复会导致数据互相覆盖。

有了 namespace，⾃然 id 就可以重复，namespace 不同，namespace+id ⾃然也就不同。

#### 3.Xml 映射⽂件中，除了常⻅的 select|insert|updae|delete 标签之外，还有哪些标签？

答：还有很多其他的标
签， <resultMap> 、 <parameterMap> 、 <sql> 、 <include> 、 <selectKey> ，加上动态 sql的 9 个标签， trim|where|set|foreach|if|choose|when|otherwise|bind 等，其中为 sql⽚段标签，通过 <include> 标签引⼊ sql ⽚段， <selectKey> 为不⽀持⾃增的主键⽣成策略标签。

#### 4.Mybatis 映射⽂件中，如果 A 标签通过 include 引⽤了 B 标签的内容，请问，B 标签能否定义在 A 标签的后⾯，还是说必须定义在 A 标签的前⾯？

答：虽然 Mybatis 解析 Xml 映射⽂件是按照顺序解析的，但是，被引⽤的 B 标签依然可以定义**在任何地⽅**，Mybatis 都可以正确识别。

原理是，Mybatis 解析 A 标签，发现 A 标签引⽤了 B 标签，但是 B 标签尚未解析到，尚不存在，此时*，Mybatis 会将 A 标签标记为未解析状态，然后继续解析余下的标签，*包含 B 标签，待所有标签解析完毕，Mybatis 会重新解析那些被标记为未解析的标签，此时再解析 A 标签时，B 标签已经存在，A标签也就可以正常解析完成了。

###  12.Dao 接⼝的⼯作原理是什么？

**最佳实践中，通常⼀个 Xml 映射⽂件，都会写⼀个 Dao 接⼝与之对应，请问，这个 Dao 接⼝的⼯作原理是什么？Dao 接⼝⾥的⽅法，参数不同时，⽅法能重载吗？**

dao接口也就是常说的Mapper接口，接口的权限名，就是映射文件的中 的namespace的值。

接口的方法名就是MappedStatement 的id 值。接口方法内的参数就是传给sql 的参数。

Mapper 接口没有实现类，当调用接口方法时，接口全限名+方法名	拼接字符串作为 key 值。可唯一定位一个 MappedStatement。

dao 接口的方法不能重载，因为是根据全限名+方法名进行寻找的。

dao接口的工作原理是 JDK 动态代理，Matis 运行时会使用 JDK 动态代理为 Dao 接口生成代理 Proxy对象，代理对象会拦截接口方法，转而执行MappedStatement 所代表的sql，然后将执行结果返回

### 13.Mybatis 是否可以映射 Enum 枚举类？

**Mybatis 可以映射枚举类，**不单可以映射枚举类，Mybatis 可以映射任何对象到表的⼀列上。

映射⽅式为⾃定义⼀个  TypeHandler ，实现  TypeHandler 的  setParameter() 和  getResult()接⼝⽅法。

 TypeHandler 有两个作⽤，⼀是完成从 javaType ⾄ jdbcType 的转换，⼆是完成jdbcType ⾄ javaType 的转换，体现为  setParameter() 和  getResult() 两个⽅法，分别代表设置 sql 问号占位符参数和获取列查询结果。

### 14.当实体类中的属性名和表中的字段名不一样，如何将查询的结果封装到指定 pojo

1. 通过在查询的sql 语句中定义字段名的别名
2. 通过<resultMap> 来映射字段名和实体类属性名的一 一 对应关系（注解开发使用@Results）

### 19.如何获取自动生成的（主）键值

配置文件设置 usegeneratedkeys 为 true

![image-20201025100037893](C:\Users\hp\Desktop\java面试题\img\image-20201025100037893.png)

### 20.resultType 和resultMap 区别

![image-20201025100127834](C:\Users\hp\Desktop\java面试题\img\image-20201025100127834.png)

### 21.使用Mybatis 的mapper 接口调用时有哪些要求

![image-20201025100255913](C:\Users\hp\Desktop\java面试题\img\image-20201025100255913.png)

###  22.jpa 和 hibernate 有什么区别？

- JPA Java Persistence API，是Java EE 5的标准ORM接口，也是ejb3规范的一部分。
- Hibernate，当今很流行的ORM框架，是JPA的一个实现，但是其功能是JPA的超集。
- JPA和Hibernate之间的关系，可以简单的理解为JPA是标准接口，Hibernate是实现。那么Hibernate是如何实现与JPA的这种关系的呢。Hibernate主要是通过三个组件来实现的，及hibernate-annotation、hibernate-entitymanager和hibernate-core。
- hibernate-annotation是Hibernate支持annotation方式配置的基础，它包括了标准的JPA annotation以及Hibernate自身特殊功能的annotation。
- hibernate-core是Hibernate的核心实现，提供了Hibernate所有的核心功能。
- hibernate-entitymanager实现了标准的JPA，可以把它看成hibernate-core和JPA之间的适配器，它并不直接提供ORM的功能，而是对hibernate-core进行封装，使得Hibernate符合JPA的规范。

### 23.为什么说 Mybatis 是半⾃动 ORM 映射⼯具？它与全⾃动的区别在哪⾥？

Hibernate 属于全⾃动 ORM 映射⼯具，使⽤ Hibernate 查询关联对象或者关联集合对象时，可以根据对象关系模型直接获取，所以它是全⾃动的。

⽽ Mybatis 在查询关联对象或关联集合对象时，需要⼿动编写 sql 来完成，所以，称之为半⾃动 ORM 映射⼯具。

### 23.如何使⽤JPA在数据库中⾮持久化⼀个字段？

```java
Entity(name="USER")
public class User {

@Id
@GeneratedValue(strategy = GenerationType.AUTO)
@Column(name = "ID")
private Long id;

@Column(name="USER_NAME")
private String userName;

@Column(name="PASSWORD")
private String password;

private String secrect;
}
```

如果我们想让 secrect 这个字段不被持久化，也就是不被数据库存储怎么办？我们可以采⽤下⾯⼏种⽅法：

```java
static String transient1; // not persistent because of static
final String transient2 = “Satish”; // not persistent because of final
transient String transient3; // not persistent because of transient
@Transient
String transient4; // not persistent because of @Transient
```















------



## ⭐设计模式⭐

### 1.单例模式

一个应用程序中，某个类的实例对象只有一个，无法通过构造函数new出来，因为构造器被private修饰，一般通过getInstance()的方法来获取他们的实例

**实现一：静态内部类**

```java
public class Singleton {
    private Singleton() {
    }

    private static class Holder {
        private static Singleton instance = new Singleton();
    }

    public static Singleton getInstance() {
        return Holder.instance;
    }
}

```

**实现二：延迟加载** / “懒汉模式”

- 延迟加载就是调用get()方法时实例才被创建（先不急着实例化出对象，等要用的时候才给你创建出来。不着急，故又称为“懒汉模式”），常见的实现方法就是在get方法中进行new实例化。

```java
public class Singleton {
    
    private static Singleton singleton; 
    
    private Singleton(){}

    public static Singleton getInstance() {
        if (instance==null){
           singleton=new Singleton();
        }
        return singleton;
    }
}
```

**“懒汉模式”的优缺点：**

- **优点：**实现起来比较简单，当类SingletonTest被加载的时候，静态变量static的instance未被创建并分配内存空间，当getInstance方法第一次被调用时，初始化instance变量，并分配内存，因此在某些特定条件下会节约了内存。
- **缺点：**在多线程环境中，这种实现方法是完全**错误**的，根本不能保证单例的状态。

**实现三：线程安全的“懒汉模式”**

```java
public class Singleton {

    // 将自身实例化对象设置为一个属性，并用static修饰
    private static Singleton instance;
    
    // 构造方法私有化
    private Singleton() {}
    
    // 静态方法返回该实例，加synchronized关键字实现同步
    public static synchronized Singleton getInstance() {
        if(instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
```

- 优点：在多线程情形下，保证了“懒汉模式”的线程安全。
- 缺点：众所周知在多线程情形下，**synchronized方法通常效率低**，显然这不是最佳的实现方案。

**实现四饿汉式**写法/立即加载

- 立即加载就是使用类的时候已经将对象创建完毕（不管以后会不会使用到该实例化对象，先创建了再说。很着急的样子，故又被称为“饿汉模式”），常见的实现办法就是直接new实例化。

```java
public class Singleton {

    // 将自身实例化对象设置为一个属性，并用static、final修饰
    private static final Singleton instance = new Singleton();
    
    // 构造方法私有化
    private Singleton() {}
    
    // 静态方法返回该实例
    public static Singleton getInstance() {
        return instance;
    }
}
```

**“饿汉模式”的优缺点：**

**优点：**实现起来简单，没有多线程同步问题。

**缺点：**当类SingletonTest被加载的时候，会初始化static的instance，静态变量被创建并分配内存空间，从这以后，这个static的instance对象便一直占着这段内存（即便你还没有用到这个实例），当类被卸载时，静态变量被摧毁，并释放所占有的内存，因此在某些特定条件下会**耗费内存。**

**实现五：双重检验锁**线程安全

```java
public class Singleton {
    // 首先，也是先堵死 new Singleton() 这条路
    private Singleton() {
    }
    
    //volatile保证可见性
    private static volatile Singleton instance=null;

    public static Singleton getInstance() {
        if (instance==null){
            //加锁
            synchronized (Singleton.class){
                // 这一次判断也是必须的，不然会有并发问题
                if (instance==null){
                    instance=new Singleton();
                }
            }
        }
        return instance;
    }
}
```

- 单例模式的最佳实现方式。内存占用率高，效率高，线程安全，多线程操作原子性。

- 双重检查，指的是两次检查 instance 是否为 null。
- volatile 在这里是需要的，希望能引起读者的关注。


### 2.观察者模式

- 在对象之间定义一个多的依赖，这样一来，当一个对象改变状态，依赖它的对象都会收到通知，并自动更新

```java
public interface Person {
    void getMessage(String s);
}

public class LaoWang implements Person {
    String name = "老王";

    @Override
    public void getMessage(String s) {
        System.out.println(name + "接到了小美打过来的电话，电话内容是：" + s);
    }
}

public class XiaoLi implements Person {
    String name = "小李";

    @Override
    public void getMessage(String s) {
        System.out.println(name + "接到了小美打过来的电话，电话内容是：-？？" + s);
    }
}

public class XiaoMei {
    List<Person> list = new ArrayList<>();

    public void addPerson(Person person) {
        list.add(person);
    }

    public void notifyPerson() {
        for (Person person : list) {
            person.getMessage("你们过来吧，谁先过来谁就能陪我一起玩儿游戏!");
        }
    }
}

public class Test {
    public static void main(String[] args) {
        XiaoMei xiaoMei = new XiaoMei();
        
        xiaoMei.addPerson(new LaoWang());
        xiaoMei.addPerson(new XiaoLi());

        xiaoMei.notifyPerson();
    }
}

```

**测试结果**

![image-20201010093337965](C:\Users\hp\Desktop\java面试题\img\image-20201010093337965.png)

### 3.装饰者模式

- 动态的将新功能附加到对象上。在对象功能扩展方面，它比继承更 有弹性，装饰者模式也体现了开闭原则(ocp)

- 可以设计出多个不同的具体装饰类，创造出多个不同行为的组合。

你去星巴克想要点一杯饮料，比如咖啡，有时候你还想往咖啡里加些调料比如牛奶、摩卡之类的，第二天你还去星巴克，想要喝点茶，这时候想要往茶里加点调料比如枸杞，这时就需要装饰者模式了

```java
//饮料类
public abstract class Beverage {
    String description = "Unkonw!!";

    public String getDescription() {
        return description;
    }

    public abstract double cost();
}

//调料类
public abstract class CondimentDecorate extends Beverage {
    @Override
    public abstract String getDescription();
}

```

**咖啡系列饮料**

```java
//浓缩咖啡
public class Espresso extends Beverage {
    public Espresso() {
        description = "浓缩咖啡";
    }

    @Override
    public double cost() {
        return 2;
    }
}

//咖啡里面加的调料 牛奶
public class Milk extends CondimentDecorate {
    private Beverage beverage;

    public Milk(Beverage beverage) {
        this.beverage = beverage;
    }
    
    @Override
    public String getDescription() {
        return beverage.getDescription() + "加牛奶";
    }

    @Override
    public double cost() {
        return beverage.cost() + 1;
    }
}

//咖啡里面加的调料 摩卡
public class Mocha extends CondimentDecorate {

    private Beverage beverage;

    public Mocha(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription()+"加 MoCha";
    }

    @Override
    public double cost() {
        return beverage.cost()+ 1.2;
    }
}
```

**茶系列饮料**

```java
public class Tea extends Beverage {
    public Tea() {
        description = "茶";
    }

    @Override
    public double cost() {
        return 2;
    }
}

//往茶里加枸杞
public class Gouqi extends CondimentDecorate {
    private Beverage beverage;

    public Gouqi(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + "加枸杞";
    }

    @Override
    public double cost() {
        return beverage.cost() + 2;
    }
}
```

**测试类**

```java
public class Test {
    public static void main(String[] args) {
        System.out.println("=====Coffee====");
        Beverage espresso=new Espresso();
        espresso=new Mocha(espresso);
        espresso=new Milk(espresso);

        System.out.println(espresso.getDescription()+"$"+espresso.cost());

        System.out.println("=====Tea====");
        Beverage tea=new Tea();
        tea=new Gouqi(tea);
        System.out.println(tea.getDescription()+"$"+tea.cost());
    }
}
```

<img src="C:\Users\hp\Desktop\java面试题\img\image-20201010102601388.png" alt="image-20201010102601388"  />

### 3.适配器模式

- 作为两个不兼容接口之间的桥梁。

![image-20201010102805488](C:\Users\hp\Desktop\java面试题\img\image-20201010102805488.png)



### **简单工厂和抽象工厂有什么区别？**

**简单工厂模式**：

- 这个模式本身很简单而且使用在业务较简单的情况下。一般用于小项目或者具体产品很少扩展的情况（这样工厂类才不用经常更改）。

**它由三种角色组成：**

- 工厂类角色：这是本模式的核心，含有一定的商业逻辑和判断逻辑，根据逻辑不同，产生具体的工厂产品。如例子中的Driver类。
- 抽象产品角色：它一般是具体产品继承的父类或者实现的接口。由接口或者抽象类来实现。如例中的Car接口。
- 具体产品角色：工厂类所创建的对象就是此角色的实例。在java中由一个具体类实现，如例子中的Benz、Bmw类。

来用类图来清晰的表示下的它们之间的关系：

![image-20201010115338604](C:\Users\hp\Desktop\java面试题\img\image-20201010115338604.png)

**抽象工厂模式：**

先来认识下什么是产品族： 位于不同产品等级结构中，功能相关联的产品组成的家族。

![image-20201010115403307](C:\Users\hp\Desktop\java面试题\img\image-20201010115403307.png)

**可以这么说，它和工厂方法模式的区别就在于需要创建对象的复杂程度上。而且抽象工厂模式是三个里面最为抽象、最具一般性的。抽象工厂模式的用意为：给客户端提供一个接口，可以创建多个产品族中的产品对象。**



而且使用抽象工厂模式还要满足一下条件：

1. 系统中有多个产品族，而系统一次只可能消费其中一族产品
2. 同属于同一个产品族的产品以其使用。
   来看看抽象工厂模式的各个角色（和工厂方法的如出一辙）：

- 抽象工厂角色： 这是工厂方法模式的核心，它与应用程序无关。是具体工厂角色必须实现的接口或者必须继承的父类。在java中它由抽象类或者接口来实现。
- 具体工厂角色：它含有和具体业务逻辑有关的代码。由应用程序调用以创建对应的具体产品的对象。在java中它由具体的类来实现。
- 抽象产品角色：它是具体产品继承的父类或者是实现的接口。在java中一般有抽象类或者接口来实现。
- 具体产品角色：具体工厂角色所创建的对象就是此角色的实例。在java中由具体的类来实现。



## ⭐JVM⭐

### 描述一下JVM加载class文件的原理机制? 

​	JVM中类的装载是由ClassLoader和它的子类来实现的,Java ClassLoader 是一个重要的Java运行时系统组件。它负责在运行时查找和装入类文件的类。

### 试举例说明一个典型的垃圾回收算法？ 

Java语言规范没有明确地说明JVM使用哪种垃圾回收算法，但是任何一种垃圾收集算法一般要做2件基本的事情：

（1）发现无用信息对象；

（2）回收被无用对象占用的内存空间，使该空间可被程序再次使用。

大多数垃圾回收算法使用了根集(root set)这个概念；所谓根集就量正在执行的Java程序可以访问的引用变量的集合(包括局部变量、参数、类变量)，程序可以使用引用变量访问对象的属性和调用对象的方法。垃圾收集首选需要确定从根开始哪些是可达的和哪些是不可达的，从根集可达的对象都是活动对象，它们不能作为垃圾被回收，这也包括从根集间接可达的对象。而根集通过任意路径不可达的对象符合垃圾收集的条件，应该被回收。

下面介绍几个常用的算法。
**引用计数法(Reference Counting Collector)**
引用计数法是唯一没有使用根集的垃圾回收算法，该算法使用引用计数器来区分存活对象和不再使用的对象。一般来说，堆中的每个对象对应一个引用计数器。当每一次创建一个对象并赋给一个变量时，引用计数器置为1。当对象被赋给任意变量时，引用计数器每次加1。当对象出了作用域后(该对象丢弃不再使用)，引用计数器减1，一旦引用计数器为0，对象就满足了垃圾收集的条件。
基于引用计数器的垃圾收集器运行较快，不会长时间中断程序执行，适宜地必须 实时运行的程序。但引用计数器增加了程序执行的开销，因为每次对象赋给新的变量 ，计数器加1，而每次现有对象出了作用域生，计数器减1。



------

------

## ⭐java并发⭐

### **1.并行和并发有什么区别？**

**并行** 

- 是指两个或者多个事件在 **同一时刻**  发生；
- 并行的多个任务之间是不互相抢占资源的、
- **其实决定并行的因素不是CPU的数量，而是CPU的核心数量，比如一个CPU多个核也可以并行。**

![image-20201003223527813](C:\Users\hp\Desktop\java面试题\img\image-20201003223527813.png)

**并发** 

- 是指两个或多个事件在 **同一时间间隔 ** 发生。

- 并发的多个任务之间是互相抢占资源的。  

- 在操作系统中，是指一个时间段中有几个程序都处于已启动运行到运行完毕之间，且这几个程序都是在同一个处理机上运行。**

  ![image-20201003223602179](C:\Users\hp\Desktop\java面试题\img\image-20201003223602179.png)

只有在多CPU或者一个CPU多核的情况中，才会发生并行。否则，看似同时发生的事情，其实都是并发执行的。

### **2. 线程和进程的区别？**

简而言之，进程是程序运行和资源分配的基本单位，一个程序至少有一个进程，一个进程至少有一个线程。进程在执行过程中拥有独立的内存单元，而多个线程共享内存资源，减少切换次数，从而效率更高。

线程是进程的一个实体，是cpu调度和分派的基本单位，是比程序更小的能独立运行的基本单位。

同一进程中的多个线程之间可以并发执行。

### **3. 守护线程是什么？**

守护线程（即daemon thread），是个服务线程，准确地来说就是服务其他的线程。

### **4.创建线程有哪几种方式？**

- 继承 Thread 类并重写 run 方法。实现简单，但不符合里氏替换原则，不可以继承其他类。
- 实现 Runnable 接口并重写 run 方法。避免了单继承局限性，编程更加灵活，实现解耦。
- 实现 Callable 接口并重写 call 方法。可以获取线程执行结果的返回值，并且可以抛出异常

**① 继承Thread类创建线程类**

- 定义Thread类的子类，并重写该类的run方法，该run方法的方法体就代表了线程要完成的任务。因此把run()方法称为执行体。
- 创建Thread子类的实例，即创建了线程对象。
- 调用线程对象的start()方法来启动该线程。

**② 通过Runnable接口创建线程类**

- 定义runnable接口的实现类，并重写该接口的run()方法，该run()方法的方法体同样是该线程的线程执行体。
- 创建 Runnable实现类的实例，并依此实例作为Thread的target来创建Thread对象，该Thread对象才是真正的线程对象。
- 调用线程对象的start()方法来启动该线程。

**③ 通过Callable和Future创建线程**

- 创建Callable接口的实现类，并实现call()方法，该call()方法将作为线程执行体，并且有返回值。
- 创建Callable实现类的实例，使用FutureTask类来包装Callable对象，该FutureTask对象封装了该Callable对象的call()方法的返回值。
- 使用FutureTask对象作为Thread对象的target创建并启动新线程。
- 调用FutureTask对象的get()方法来获得子线程执行结束后的返回值。

### **5.说一下 runnable 和 callable 有什么区别？**

- Runnable接口中的run()方法的返回值是void，它做的事情只是纯粹地去执行run()方法中的代码而已；
- Callable接口中的call()方法是有返回值的，是一个泛型，和Future、FutureTask配合可以用来获取异步执行的结果。







### **6.线程的基本状态**

**线程通常都有五种状态，创建、就绪、运行、阻塞和死亡。**

**New** （新建） 在生成线程对象，并没有调用该对象的start方法，这是线程处于创建状态。

**Runnable**（运行）  线程调度程序将处于就绪状态的线程设置为当前线程，此时线程就进入了运行状态，开始运行run函数当中的代码。

**就绪状态。**当调用了线程对象的start方法之后，该线程就进入了就绪状态，但是此时线程调度程序还没有把该线程设置为当前线程，此时处于就绪状态。在线程运行之后，从等待或者睡眠中回来之后，也会处于就绪状态。

- **Waiting**（无限期等待） 不会被分配处理器执行时间，他们要等待被其他线程显示唤醒
- **Time Waiting**（限期等待） 不会被分配处理器执行时间，在一定时间后会由系统自动唤醒

**Blocked**（阻塞） 阻塞状态是指线程正在运行的时候，被暂停。通常是为了等待某个时间的发生(比如说某项资源就绪)之后再继续运行。sleep,suspend，wait等方法都可以导致线程阻塞。

**Terminated**（死亡） 如果一个线程的run方法执行结束或者调用stop方法后，该线程就会死亡。对于已经死亡的线程，无法再使用start方法令其进入就绪 

### **7.状态之间的关系**

![image-20201003164621243](C:\Users\hp\Desktop\java面试题\img\image-20201003164621243.png)

### 8.sleep() 和 wait() 有什么区别? 

**sleep()：**

- 方法是线程类（Thread）的静态方法，让调用线程进入睡眠状态，让出执行机会给其他线程，等到休眠时间结束后，**线程进入就绪状态和其他线程一起竞争cpu的执行时间。**因为sleep() 是static静态的方法，他不能改变对象的机锁
- 当一个 synchronized 块中调用了sleep() 方法，线程虽然进入休眠，但是对象的机锁没有被释放，其他线程依然无法访问这个对象。

**wait()：**

- wait()是Object类的方法，当一个线程执行到wait方法时，它就进入到一个和该对象相关的等待池，**同时释放对象的机锁**，使得其他线程能够访问，可以通过notify，notifyAll方法来唤醒等待的线程

### **9.notify() 和 notifyAll() 有什么区别？**

- 如果线程调用了对象的 wait()方法，那么线程便会处于该对象的等待池中，等待池中的线程不会去竞争该对象的锁。
- 当有线程调用了对象的 **notifyAll()方法（唤醒所有 wait 线程）**或 notify()方法（只随机唤醒一个 wait 线程），被唤醒的的线程便会进入**该对象的锁池中**，**锁池中的线程会去竞争该对象锁。**也就是说，调用了notify后只要一个线程会由等待池进入锁池，而notifyAll会将该对象等待池内的所有线程移动到锁池中，等待锁竞争。
- 优先级高的线程竞争到对象锁的概率大，假若某线程没有竞争到该对象锁，它还会留在锁池中，**唯有线程再次调用 wait()方法，它才会重新回到等待池中。**而竞争到对象锁的线程则继续往下执行，直到执行完了 synchronized 代码块，它会释放掉该对象锁，这时锁池中的线程会继续竞争该对象锁。

### **10.线程的 run()和 start()有什么区别？**

每个线程都是通过某个特定Thread对象所对应的方法run()来完成其操作的，方法run()称为线程体。通过调用Thread类的start()方法来启动一个线程。

**start()** 

- 方法来启动一个线程，真正实现了多线程运行。这时无需等待run方法体代码执行完毕，可以直接继续执行下面的代码； 这时此线程是处于就绪状态， 并没有运行。 然后通过此Thread类调用方法run()来完成其运行状态， 这里方法run()称为线程体，它包含了要执行的这个线程的内容， Run方法运行结束， 此线程终止。然后CPU再调度其它线程。

**run()**

- 方法是在本线程里的，只是线程里的一个函数,而不是多线程的。 如果直接调用run(),其实就相当于是调用了一个普通函数而已，直接待用run()方法必须等待run()方法执行完毕才能执行下面的代码，所以执行路径还是只有一条，根本就没有线程的特征，所以在多线程执行时要使用start()方法而不是run()方法。

### 11.启动一个线程是用run()还是start()? 

- 启动一个线程是调用start()方法，使线程所代表的虚拟处理机处于可运行状态，这意味着它可以由JVM调度并执行。这并不意味着线程就会立即运行。
- run()方法可以产生必须退出的标志来停止一个线程。 

### 12.线程的同步

#### **为何要使用同步？**  

java允许多线程并发控制，当多个线程同时操作一个可共享的资源变量时（如数据的增删改查），将会导致数据不准确，相互之间产生冲突，因此加入同步锁以避免在该线程没有完成操作之前，被其他线程的调用， 从而保证了该变量的唯一性和准确性。

#### **如何实现同步？**

1. **使用synchronized 关键字**

即有synchronized关键字修饰的方法（所有访问状态变量的方法都必须进行同步），此时充当锁的对象为调用同步方法的对象。在调用该方法前，需要获得内置锁，否则就处于阻塞状态。如下代码：

```java
public class Test{
	private int count = 0;
	
	public synchronized int add(){
		count += 1;
		return count;
	}
	
	public synchronized int delete(){
		count -= 1;
		return count;
	}
}
```

2. **同步代码块**

   即有synchronized关键字修饰的语句块。锁的粒度更细，并且充当锁的对象不一定是this，也可以是其它对象，使用起来更加灵活。

   ```java
   public class Test{
   	private int count = 0;
   	
   	public int add(){
   		synchronized(this){
   			count += 1;
   		}
   		return count;
   	}
   	
   	public int delete(){
   		synchronized(this){
   			count -= 1;
   		}
   		return count;
   	}
   }
   ```

   同步是一种高开销的操作，因此应该尽量减少同步的内容。通常没有必要同步整个方法，使用synchronized代码块同步关键代码即可。下面看几个例子：



### 13.请说出你所知道的线程同步的方法。 

- wait():使一个线程处于等待状态，并且释放所持有的对象的lock。 
- sleep():使一个正在运行的线程处于睡眠状态，是一个静态方法，调用此方法要捕捉InterruptedException异常。 
- notify():唤醒一个处于等待状态的线程，注意的是在调用此方法的时候，并不能确切的唤醒某一个等待状态的线程，而是由JVM确定唤醒哪个线程，而且不是按优先级。 
- Allnotity():唤醒所有处入等待状态的线程，注意并不是给所有唤醒线程一个对象的锁，而是让它们竞争。 

### **14.创建线程池有哪几种方式？**

**① newFixedThreadPool(int nThreads)**

创建一个固定长度的线程池，每当提交一个任务就创建一个线程，直到达到线程池的最大数量，这时线程规模将不再变化，当线程发生未预期的错误而结束时，线程池会补充一个新的线程。

**② newCachedThreadPool()**

创建一个可缓存的线程池，如果线程池的规模超过了处理需求，将自动回收空闲线程，而当需求增加时，则可以自动添加新线程，线程池的规模不存在任何限制。

**③ newSingleThreadExecutor()**

这是一个单线程的Executor，它创建单个工作线程来执行任务，如果这个线程异常结束，会创建一个新的来替代它；它的特点是能确保依照任务在队列中的顺序来串行执行。

**④ newScheduledThreadPool(int corePoolSize)**

创建了一个固定长度的线程池，而且以延迟或定时的方式来执行任务，类似于Timer。

### **15.线程池都有哪些状态？**

![image-20201003233221395](C:\Users\hp\Desktop\java面试题\img\image-20201003233221395.png)

### **16.线程池中 submit()和 execute()方法有什么区别？**

- 接收的参数不一样
- submit有返回值，而execute没有
- submit方便Exception处理

### **17.在 java 程序中怎么保证多线程的运行安全？**

**线程安全在三个方面体现：**

- 原子性：提供互斥访问，同一时刻只能有一个线程对数据进行操作，（atomic,synchronized）；
- 可见性：一个线程对主内存的修改可以及时地被其他线程看到，（synchronized,volatile）；
- 有序性：一个线程观察其他线程中的指令执行顺序，由于指令重排序，该观察结果一般杂乱无序，（happens-before原则）。

### **18.多线程锁的升级原理是什么？**

在Java中，锁共有4种状态，级别从低到高依次为：无状态锁，偏向锁，轻量级锁和重量级锁状态，这几个状态会随着竞争情况逐渐升级。锁可以升级但不能降级。

锁升级的图示过程：

![image-20201003233537454](C:\Users\hp\Desktop\java面试题\img\image-20201003233537454.png)



### **19.什么是死锁？**

死锁是指两个或两个以上的进程在执行过程中，由于竞争资源或者由于彼此通信而造成的一种阻塞的现象，若无外力作用，它们都将无法推进下去。

此时称系统处于死锁状态或系统产生了死锁，这些永远在互相等待的进程称为死锁进程。

是操作系统层面的一个错误，是**进程死锁的简称**，最早在 *1965 年由 Dijkstra 在研究银行家算法时提出的*，它是计算机操作系统乃至整个并发程序设计领域最难处理的问题之一。

### **20.怎么防止死锁？**

**死锁的四个必要条件：**

- **互斥条件：**
  - 进程对所分配到的资源**不允许其他进程进行访问**，若其他进程访问该资源，只能等待，直至占有该资源的进程使用完成后释放该资源
- **请求和保持条件：**
  - 进程获得一定的资源之后，又对其他资源发出请求，但是该资源可能被其他进程占有，此事请求阻塞，但又对自己获得的资源保持不放
- **不可剥夺条件：**
  - 是指进程已获得的资源，在未完成使用之前，不可被剥夺，只能在使用完后自己释放
- **环路等待条件：**
  - 是指进程发生死锁后，若干进程之间形成一种头尾相接的循环等待资源关系

这四个条件是死锁的必要条件，只要系统发生死锁，这些条件必然成立，而**只要上述条件之 一不满足，就不会发生死锁。**

理解了死锁的原因，尤其是产生死锁的四个必要条件，就可以最大可能地避免、预防和 解除死锁。

所以，在系统设计、进程调度等方面注意如何不让这四个必要条件成立，如何确 定资源的合理分配算法，避免进程永久占据系统资源。

此外，也要防止进程在处于等待状态的情况下占用资源。因此，对资源的分配要给予合理的规划。

### **20.ThreadLocal 是什么？有哪些使用场景？**

线程局部变量是局限于线程内部的变量，属于线程自身所有，不在多个线程间共享。

Java提供ThreadLocal类来支持线程局部变量，是一种实现线程安全的方式。但是在管理环境下（如 web 服务器）使用线程局部变量的时候要特别小心，在这种情况下，工作线程的生命周期比任何应用变量的生命周期都要长。任何线程局部变量一旦在工作完成后没有释放，Java 应用就存在内存泄露的风险。

### **21.说一下 synchronized 底层实现原理？**

synchronized可以保证方法或者代码块在运行时，**同一时刻只有一个方法可以进入到临界区，**        **同时它还可以保证共享变量的内存可见性。**

synchronized 是由JVM 实现的一种互斥同步的方式，通过字节码会发现被 synchronized 修饰的程序段会被 **monitorenter 和 monitorexit** 两个字节码指令包住。

**Java中每一个对象都可以作为锁，这是synchronized实现同步的基础：**

- 普通同步方法，锁是当前实例对象
- 静态同步方法，锁是当前类的class对象
- 同步方法块，锁是括号里面的对象

###  **21.1 monitorenter 和 monitorexit 分别是什么意思**

![image-20201025165212966](C:\Users\hp\Desktop\java面试题\img\image-20201025165212966.png)

### **22. synchronized 和 volatile 的区别是什么？**

- volatile本质是在告诉jvm当前变量在寄存器（工作内存）中的值是不确定的，需要从主存中读取； synchronized则是锁定当前变量，只有当前线程可以访问该变量，其他线程被阻塞住。
- **volatile仅能使用在变量级别**；synchronized则可以使用在变量、方法、和类级别的。
- volatile仅能实现变量的修改可见性，不能保证原子性；而synchronized则可以保证变量的**修改可见性和原子性。**
- **volatile不会造成线程的阻塞**；synchronized可能会造成线程的阻塞。
- **volatile标记的变量不会被编译器优化**；synchronized标记的变量可以被编译器优化

### **23.synchronized 和 Lock 有什么区别？**

- 首先synchronized是**java内置关键字**，在jvm层面，**Lock是个java类；**
- synchronized无法判断是否获取锁的状态，Lock可以判断是否获取到锁；
- synchronized 会自动释放锁(a 线程执行完同步代码会释放锁 ；b 线程执行过程中发生异常会释放锁)，**Lock需在finally中手工释放锁（unlock()方法释放锁），否则容易造成线程死锁；**
- 用synchronized关键字的两个线程1和线程2，如果当前线程1获得锁，线程2线程等待。如果线程1阻塞，线程2则会一直等待下去，而**Lock锁就不一定会等待下去，如果尝试获取不到锁，线程可以不用一直等待就结束了；**
- synchronized的锁**可重入、不可中断、非公平，**而Lock锁可重入、可判断、可公平（两者皆可）；
- Lock锁适合**大量同步**的代码的同步问题，synchronized锁适合**代码少量的同步**问题。

### **24.synchronized 和 ReentrantLock 区别是什么？**

synchronized是和if、else、for、while一样的**关键字**，**ReentrantLock是类**，这是二者的本质区别。

既然ReentrantLock是类，那么它就提供了比synchronized更多更灵活的特性，可以被继承、可以有方法、可以有各种各样的类变量，ReentrantLock比synchronized的扩展性体现在几点上： 

- ReentrantLock可以对获取锁的等待时间进行设置，这样就避免了死锁 
- ReentrantLock可以获取各种锁的信息
- ReentrantLock可以灵活地实现多路通知 

**另外，二者的锁机制其实也是不一样的:**

- ReentrantLock底层调用的是Unsafe的park方法加锁，
- synchronized操作的应该是对象头中mark word。

### 25.Synchonized 和 ThreadLocal 的区别

**相同点** 都用于解决多线程并发访问，防止任务在共享资源上产生冲突

**不同点**： 

- **Synchronized** 用于实现同步机制，是利用锁的机制使变量或代码块在某一时刻只能被一个线程访问，是一种 **以时间换空间** 的方式
- **ThreadLocal** 为每一个线程都提供了变量的副本，使得每个线程在某一个时间访问到的并不是同一个对象，根除了对变量的共享，是一种 **以空间换时间**的方式

### 25.1ThreadLocal 是怎么解决并发安全的

ThreadLocal 为每一个线程维护变量的副本，把共享数据可见性范围限制在同一个线程内

实现原理:  在ThreadLocal 里存在一个Map 用于存储每一个线程的变量的副本

**一图胜千言**

![image-20201025231741349](C:\Users\hp\Desktop\java面试题\img\image-20201025231741349.png)

### 25.2Thread Local 需要注意什么

要注意remove：

ThreadLocal 的实现是基于一个所谓的 ThreadLocalMap ，在其中，它的key 是一个弱引用。

通常弱引用都会和引用队列配合清理机制使用，但是 ThreadLocal 没有这么做

这意味着，废弃项目的回收依赖于显示地触发，否则就要等待线程结束，进而回收相应 ThreadLocal ，这就是很多OOM 的来源。所以都会建议应用一定要自己负责 remove ，并且不要和线程池配合，因为worker 线程往往是不会退出的。

### 26.JVM 对 Java 原生锁做了哪些优化

![image-20201025170143406](C:\Users\hp\Desktop\java面试题\img\image-20201025170143406.png)

![image-20201025170209568](C:\Users\hp\Desktop\java面试题\img\image-20201025170209568.png)

### 27.为什么说 Synchronized 是非公平锁

非公平主要表现在获取锁的行为上，并非是按照申请锁的时间前后，给等待线程分配锁，每当锁被释放后，任何一个线程都有机会竞争到锁，这样的目的是为了提高执行性能，缺点是可能会产生线程饥饿线像。

### 28.什么是锁消除和锁粗化？

**锁消除：**指虚拟机即时编译（JIT）在运行时，对一些代码上要求同步，但被检查到不可能存在共享数据竞争的锁进行消除。主要根据逃逸分析

**锁粗化：**原则上，同步块的作用范围要尽量小。如果一系列的操作都对同一个对象反复加锁和解锁，甚至加锁操作在循环体内，频繁的进行互斥同步也会导致不必要的性能损耗。

锁粗化实质就是增大锁的作用域

### 29.CAS

![image-20201025172628872](C:\Users\hp\Desktop\java面试题\img\image-20201025172628872.png)

### 30.ABA问题、乐观锁的缺点

![image-20201025172913644](C:\Users\hp\Desktop\java面试题\img\image-20201025172913644.png)

### 31.AQS 问题

AQS(abstract Queued Synchronizer)类是一个用来构建锁和同步器的框架，各种Lock 包中的锁(如ReentrantLock、ReadWriteLock)，以及其他如 Semaphore、CountDownLatch，甚至早期的FutureTask 等，都是基于AQS构建的

![image-20201025202718404](C:\Users\hp\Desktop\java面试题\img\image-20201025202718404.png)

### 32.ReentrantLock 是如何实现可重入性的？

![image-20201025211622999](C:\Users\hp\Desktop\java面试题\img\image-20201025211622999.png)

### 33.如何让 Java 的线程彼此同步？ 

JUC 同步器的三个主要成员：CountSownLatch、CyclicBarrier和 Semaphore

![image-20201025212038929](C:\Users\hp\Desktop\java面试题\img\image-20201025212038929.png)



![image-20201025212129600](C:\Users\hp\Desktop\java面试题\img\image-20201025212129600.png)

```java
public class TestCyclicBarrier{
    private CyclicBarrier cyclicbarrier= new CyclicBarrier(5);
    
    public static void main(String [] args){
        new TestCyclicBarrier().begin();
    }
    
    public void begin(){
        for(int i =0;i< 5; i++){
            new Thread(new Student()).start();
        }
    }
    static class Student implements Runnable{
        @Override
        public void run(){
            try{
                Thread.sleep(2000);//正在前往饭店的路上
                cyclicBarrier.await();//到了就等着，等其他人都到了
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
```

![image-20201025213335772](C:\Users\hp\Desktop\java面试题\img\image-20201025213335772.png)

```java
public class TestSemaphore {
    public static void main(String[] args) {
        //5台机器  即5个许可证
        Semaphore semaphore = new Semaphore(5);
        for (int worker = 0; worker < 8; worker++) {
            new Worker(worker, semaphore).start();
        }
    }

    static class Worker extends Thread {
        private int num;
        private Semaphore semaphore;

        public Worker(int num, Semaphore semaphore) {
            this.num = num;
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            try {
                semaphore.acquire();//抢许可
                System.out.println(num+"");
                Thread.sleep(2000);
                System.out.println("释放");
                semaphore.release();//释放许可
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
```

![image-20201025214752590](C:\Users\hp\Desktop\java面试题\img\image-20201025214752590.png)

### 34.CountDownLatch、CyclicBarrier区别

CyclicBarrier：生活中的例子，咱们三个人明天中午食堂碰面，都到齐后，商量下火锅吃油碟还是麻酱

**作用不同：**CyclicBarrier 要等固定数量的线程都到达了栅栏位置才能继续执行，而.CountDownLatch 只需等待数字到0.	

CountDownLatch 用于事件，而CyclicBarrier 用于线程

**可用性不同**：CountDownLatch在倒数到0时出发门闩打开后，不能在次使用了。除非创建新的实例。而CyclicBarrier 可以重复使用

### 35.Java中的线程池是如何实现的

![image-20201025215831277](C:\Users\hp\Desktop\java面试题\img\image-20201025215831277.png)

### 36.创建线程池的几个核心构造参数？

![image-20201025220003379](C:\Users\hp\Desktop\java面试题\img\image-20201025220003379.png)

### 37.线程池中线程是怎么创建的？

![image-20201025220226597](C:\Users\hp\Desktop\java面试题\img\image-20201025220226597.png)

### 38.线程池的异同

**SingleThreadExecutor 线程池**

- 只有一个核心线程在工作，相当于单线程执行所有任务。
- 如果这唯一一个线程因为异常结束，那么会有一个新的线程来替代它。
- 此线程保证所有任务的执行顺序按照任务的提交顺序执行

![image-20201025220928875](C:\Users\hp\Desktop\java面试题\img\image-20201025220928875.png)

**FixedThreadPool 线程池**

- 是一个固定大小的线程池，只有核心线程
- 每次提交一个任务就创建一个线程，直到线程达到线程池的最大大小。
- 线程池一旦达到最大值就保持不变，如果某个线程因为执行异常而结束，那么线程池会补充一个新线程
- 多用于一些很稳定、很固定的正规并发线程，多用于服务器

![image-20201025221331258](C:\Users\hp\Desktop\java面试题\img\image-20201025221331258.png)

**CachedThreadPool 线程池**

- 无界线程池，如果线程池的大小超过了处理任务所需要的线程，那么就会回收部分空闲（60秒不执行的任务）线程，当任务数增加时，此线程又可以智能的添加新线程来处理任务
- 线程池的大小完全依赖于操作系统能够创建的最大线程大小。
- SynchronousQueue 是一个缓冲区为 1 的阻塞队列
- 通常用于执行一些生存期很短的异步性任务，因此一些面向连接的daemon 型 Server 中用的不多

![image-20201025223921114](C:\Users\hp\Desktop\java面试题\img\image-20201025223921114.png)

**ScheduledThreadPool 线程池**

- 核心线程池固定，大小无限的线程池。
- 此线程支持定时以及周期性执行任务的需求
- 创建一个周期性执行的线程池
- 如果闲置，非线程池会在 DEFAULT_KEEPALIVEMILLIS 时间内回收

![image-20201025224310950](C:\Users\hp\Desktop\java面试题\img\image-20201025224310950.png)

### 39.线程池的数量该设置为多少合适？

![image-20201025220612824](C:\Users\hp\Desktop\java面试题\img\image-20201025220612824.png)

### 40.如何在线程池中提交线程

![image-20201025224415006](C:\Users\hp\Desktop\java面试题\img\image-20201025224415006.png)

### 41.什么是Java 内存模型

![image-20201025225027771](C:\Users\hp\Desktop\java面试题\img\image-20201025225027771.png)

### 42todo.锁的升、降级

### 43.volatile 有什么特点，为什么它能保证变量对所有的线程可见性

volatile 是 Java 虚拟机提供的轻量级的同步机制。

当一个变量被volatile 修饰

- 保证变量被所有线程的可见性。当一条线程修改了这个变量的值，新的值对于其他线程是可以立即得知的。
- 禁止指令重排序优化： 普通变量仅仅能保证在该方法执行过程中，得到正确结果，但是不能保证程序代码的执行顺序。

### 44.Java 内存模型定义的8 种内存间操作

![image-20201025225859283](C:\Users\hp\Desktop\java面试题\img\image-20201025225859283.png)

### 45.volatile是否可以保证并发安全

不可以。虽然volatile 保证了一致性但是 java 里面的运算并非是原子操作，导致了volatile 在并发下是不安全的。

### 46.说一下 atomic 的原理？

Atomic包中的类基本的特性就是在多线程环境下，当有多个线程同时对单个（包括基本类型及引用类型）变量进行操作时，具有排他性，**即当多个线程同时对该变量的值进行更新时，仅有一个线程能成功，**而未成功的线程可以向**自旋锁一样，继续尝试，一直等到执行成功。**

Atomic系列的类中的核心方法都会调用unsafe类中的几个本地方法。我们需要先知道一个东西就是Unsafe类，**全名为：sun.misc.Unsafe**，这个类包含了大量的对C代码的操作，包括很多直接内存分配以及原子操作的调用，而它之所以标记为非安全的，是告诉你这个里面大量的方法调用都会存在安全隐患，需要小心使用，否则会导致严重的后果，例如在通过unsafe分配内存的时候，如果自己指定某些区域可能会导致一些类似C++一样的指针越界到其他进程的问题。











































## ⭐RabbitMQ⭐

### **1.rabbitmq 的使用场景有哪些？**

①. 跨系统的异步通信，所有需要异步交互的地方都可以使用消息队列。就像我们除了打电话（同步）以外，还需要发短信，发电子邮件（异步）的通讯方式。

②. 多个应用之间的耦合，由于消息是平台无关和语言无关的，而且语义上也不再是函数调用，因此更适合作为多个应用之间的松耦合的接口。基于消息队列的耦合，不需要发送方和接收方同时在线。在企业应用集成（EAI）中，文件传输，共享数据库，消息队列，远程过程调用都可以作为集成的方法。

③. 应用内的同步变异步，比如订单处理，就可以由前端应用将订单信息放到队列，后端应用从队列里依次获得消息处理，高峰时的大量订单可以积压在队列里慢慢处理掉。由于同步通常意味着阻塞，而大量线程的阻塞会降低计算机的性能。

④. 消息驱动的架构（EDA），系统分解为消息队列，和消息制造者和消息消费者，一个处理流程可以根据需要拆成多个阶段（Stage），阶段之间用队列连接起来，前一个阶段处理的结果放入队列，后一个阶段从队列中获取消息继续处理。

⑤. 应用需要更灵活的耦合方式，如发布订阅，比如可以指定路由规则。

⑥. 跨局域网，甚至跨城市的通讯（CDN行业），比如北京机房与广州机房的应用程序的通信。

### **2.rabbitmq 有哪些重要的角色？**

RabbitMQ 中重要的角色有：生产者、消费者和代理：

- 生产者：消息的创建者，负责创建和推送数据到消息服务器；
- 消费者：消息的接收方，用于处理数据和确认消息；
- 代理：就是 RabbitMQ 本身，用于扮演“快递”的角色，本身不生产消息，只是扮演“快递”的角色。

### **3.rabbitmq 有哪些重要的组件？**

- ConnectionFactory（连接管理器）：应用程序与Rabbit之间建立连接的管理器，程序代码中使用。
- Channel（信道）：消息推送使用的通道。
- Exchange（交换器）：用于接受、分配消息。
- Queue（队列）：用于存储生产者的消息。
- RoutingKey（路由键）：用于把生成者的数据分配到交换器上。
- BindingKey（绑定键）：用于把交换器的消息绑定到队列上。

### **4.rabbitmq 中 vhost 的作用是什么？**

vhost 可以理解为虚拟 broker ，即 mini-RabbitMQ server。其内部均含有独立的 queue、exchange 和 binding 等，但最最重要的是，其拥有独立的权限系统，可以做到 vhost 范围的用户控制。当然，从 RabbitMQ 的全局角度，vhost 可以作为不同权限隔离的手段（一个典型的例子就是不同的应用可以跑在不同的 vhost 中）。

### **5.rabbitmq 的消息是怎么发送的？**

首先客户端必须连接到 RabbitMQ 服务器才能发布和消费消息，客户端和 rabbit server 之间会创建一个 tcp 连接，一旦 tcp 打开并通过了认证（认证就是你发送给 rabbit 服务器的用户名和密码），你的客户端和 RabbitMQ 就创建了一条 amqp 信道（channel），信道是创建在“真实” tcp 上的虚拟连接，amqp 命令都是通过信道发送出去的，每个信道都会有一个唯一的 id，不论是发布消息，订阅队列都是通过这个信道完成的。

### **6.rabbitmq 怎么保证消息的稳定性？**

- 提供了事务的功能。
- 通过将 channel 设置为 confirm（确认）模式。

### 7.rabbitmq 怎么避免消息丢失？

- 消息持久化
- ACK确认机制
- 设置集群镜像模式
- 消息补偿机制

### **8.要保证消息持久化成功的条件有哪些？**

1. 声明队列必须设置持久化 durable 设置为 true.
2. 消息推送投递模式必须设置持久化，deliveryMode 设置为 2（持久）。
3. 消息已经到达持久化交换器。
4. 消息已经到达持久化队列。

以上四个条件都满足才能保证消息持久化成功。

### **9.rabbitmq 持久化有什么缺点？**

持久化的缺点就是降低了服务器的吞吐量，因为使用的是磁盘而非内存存储，从而降低了吞吐量。可尽量使用 ssd 硬盘来缓解吞吐量的问题。

### **10rabbitmq 有几种广播类型？**

三种广播模式：

1. fanout: 所有bind到此exchange的queue都可以接收消息（纯广播，绑定到RabbitMQ的接受者都能收到消息）；
2. direct: 通过routingKey和exchange决定的那个唯一的queue可以接收消息；
3. topic:所有符合routingKey(此时可以是一个表达式)的routingKey所bind的queue可以接收消息；

###  **11.rabbitmq 怎么实现延迟消息队列？**

1. 通过消息过期后进入死信交换器，再由交换器转发到延迟消费队列，实现延迟功能；
2. 使用 RabbitMQ-delayed-message-exchange 插件实现延迟功能。

### **12.rabbitmq 集群有什么用？**

集群主要有以下两个用途：

- 高可用：某个服务器出现问题，整个 RabbitMQ 还可以继续使用；
- 高容量：集群可以承载更多的消息量

### **13.rabbitmq 节点的类型有哪些？**

- 磁盘节点：消息会存储到磁盘。
- 内存节点：消息都存储在内存中，重启服务器消息丢失，性能高于磁盘类型。

### **14.rabbitmq 集群搭建需要注意哪些问题？**

- 各节点之间使用“--link”连接，此属性不能忽略。
- 各节点使用的 erlang cookie 值必须相同，此值相当于“秘钥”的功能，用于各节点的认证。
- 整个集群中必须包含一个磁盘节点。

### **15.rabbitmq 每个节点是其他节点的完整拷贝吗？为什么？**

不是，原因有以下两个：

1. **存储空间的考虑：**如果每个节点都拥有所有队列的完全拷贝，这样新增节点不但没有新增存储空间，反而增加了更多的冗余数据；
2. **性能的考虑：**如果每条消息都需要完整拷贝到每一个集群节点，那新增节点并没有提升处理消息的能力，最多是保持和单节点相同的性能甚至是更糟。

### **16.rabbitmq 集群中唯一一个磁盘节点崩溃了会发生什么情况？**

如果唯一磁盘的磁盘节点崩溃了，不能进行以下操作：

- 不能创建队列
- 不能创建交换器
- 不能创建绑定
- 不能添加用户
- 不能更改权限
- 不能添加和删除集群节点

**唯一磁盘节点崩溃了，集群是可以保持运行的，但你不能更改任何东西。**

### **17.rabbitmq 对集群节点停止顺序有要求吗？**

RabbitMQ 对集群的停止的顺序是有要求的，应该先关闭内存节点，最后再关闭磁盘节点。如果顺序恰好相反的话，可能会造成消息的丢失。

## ⭐zookeeper⭐

### **1. zookeeper 是什么？**

zookeeper 是一个分布式的，开放源码的分布式应用程序协调服务，是 google chubby 的开源实现，是 hadoop 和 hbase 的重要组件。

它是一个为分布式应用提供一致性服务的软件，提供的功能包括：配置维护、域名服务、分布式同步、组服务等。

### **2.zookeeper 都有哪些功能？**

- 集群管理：监控节点存活状态、运行请求等。
- 主节点选举：主节点挂掉了之后可以从备用的节点开始新一轮选主，主节点选举说的就是这个选举的过程，使用 zookeeper 可以协助完成这个过程。
- 分布式锁：zookeeper 提供两种锁：独占锁、共享锁。独占锁即一次只能有一个线程使用资源，共享锁是读锁共享，读写互斥，即可以有多线线程同时读同一个资源，如果要使用写锁也只能有一个线程使用。zookeeper可以对分布式锁进行控制。
- 命名服务：在分布式系统中，通过使用命名服务，客户端应用能够根据指定名字来获取资源或服务的地址，提供者等信息。

### **3.zookeeper 有几种部署模式？**

zookeeper 有三种部署模式：

- 单机部署：一台集群上运行；
- 集群部署：多台集群运行；
- 伪集群部署：一台集群启动多个 zookeeper 实例运行。

### **4.zookeeper 怎么保证主从节点的状态同步？**

zookeeper 的核心是原子广播，这个机制保证了各个 server 之间的同步。实现这个机制的协议叫做 zab 协议。 

zab 协议有两种模式，分别是**恢复模式（选主）**和**广播模式（同步）**。当服务启动或者在领导者崩溃后，zab 就进入了恢复模式，当领导者被选举出来，且大多数 server 完成了和 leader 的状态同步以后，恢复模式就结束了。状态同步保证了 leader 和 server 具有相同的系统状态。

### **5.集群中为什么要有主节点？**

在分布式环境中，有些业务逻辑只需要集群中的某一台机器进行执行，其他的机器可以共享这个结果，这样可以**大大减少重复计算，提高性能**，所以就需要主节点。

### **6.集群中有 3 台服务器，其中一个节点宕机，这个时候 zookeeper 还可以使用吗？**

可以继续使用，单数服务器只要没超过一半的服务器宕机就可以继续使用。

### **7.说一下 zookeeper 的通知机制？**

客户端会对某个 znode 建立一个 watcher 事件，当该 znode 发生变化时，这些客户端会收到 zookeeper 的通知，然后客户端可以根据 znode 变化来做出业务上的改变。

## ⭐Dubbo⭐

![image-20201029103508602](C:\Users\hp\Desktop\java面试题\img\image-20201029103508602.png)

## ⭐MySQL⭐

### 0.JDBC调用数据库的基本步骤 

### 1.事务是什么？ 

事务是作为一个逻辑单元执行的一系列操作，一个逻辑工作单元必须有四个属性，称为 ACID（原子性、一致性、隔离性和持久性）属性，只有这样才能成为一个事务： 

###  2.并发访问时，在数据库如何安全地修改同一行数据

1. **使用悲观锁。MySQL有两种实现方法**

```
select ... for update
select ... lock in share mode 
```

**select  ... for update 实现方式**

```mysql
set autocommit = 0; //关闭自动提交
begin; //开启事务
select * from user where id = 1 for update; 查询信息
update user set name = '张三'; //修改信息
commit ; //提交事务
```

执行select ... for update 时，一般的 select 查询 则不受影响

**lock in share mode 实现方式**

```mysql
set autocommit = 0; //关闭自动提交
begin; //开启事务
select * from user where id =1 lock in share mode;//锁定查询的字段
update user set name = '张三'; //修改信息
commit ; //提交事务
```

**二者区别**

- 前者属于共享锁，允许其他事务添加共享锁，不允许其他事物修改或者加排他锁
- 后者属于排他锁，不允许其他事物添加共享锁和排他锁，也不允许修改

2. **使用乐观锁**

   采用带版本号（version）更新，相当于CAS思想

### **3.数据库的三范式是什么？**

- 第一范式：强调的是列的原子性，即数据库表的每一列都是不可分割的原子数据项。
- 第二范式：要求实体的属性完全依赖于主关键字。所谓完全依赖是指不能存在仅依赖主关键字一部分的属性。
- 第三范式：任何非主属性不依赖于其它非主属性。

### **❤4.一张自增表里面总共有 7 条数据，删除了最后 2 条数据，重启 mysql 数据库，又插入了一条数据，此时 id 是几？**

**表类型如果是 MyISAM ，那 id 就是 8。**

- 因为MyISAM 会把自增主键的最大ID 记录到数据库文件里面，重启MySQL后，自增主键的最大ID也不会丢失。

**表类型如果是 InnoDB，那 id 就是 6。**

- 因为如果不重启，这条记录就是8，但是如果重启MySQL 的话，这条记录就是6，因为InnoDB把自增主键的最大ID 记录存在内存中，所以重启会使最大ID丢失

### **5.如何获取当前数据库版本？**

　　使用 select version() 获取当前 MySQL 数据库版本。

### 6.说一下ACID是什么

**Atomicity**（原子性）

- 一个事务中的所有操作，要么全都执行，要么全都不执行。即，事务不可分割、不可约简。 

**Consistency**（一致性） 

- 在事务开始之前和事务结束之后数据库的完整性没有被破坏。

**Isolation**（隔离性）

- 数据库允许多个 并发事务 同时 对其数据进行读写和修改的能力，隔离性可以防止多个事务并发执行时由于交叉执行而导致的数据不一致。
- 事务隔离分为不同级别，包括读未提交（Read uncommitted）、读提交（read committed）、可重复读（repeatable read）和串行化（Serializable）。 

**Durability（持久性）** 

- 事务处理结束后，对数据的修改是永久的。即便系统出现故障也不会丢失数据

### **7.char 和 varchar 的区别是什么？**

- **char(n)** ：固定长度类型，比如订阅 char(10)，当你输入"abc"三个字符的时候，它们占的空间还是 10 个字节，其他 7 个是空字节。

　　char 优点：效率高；缺点：占用空间；适用场景：存储密码的 md5 值，固定长度的，使用 char 非常合适。

- **varchar(n)** ：可变长度，存储的值是每个值占用的字节再加上一个**用来记录其长度的字节的长度。**

　　所以，从空间上考虑 varcahr 比较合适；从效率上考虑 char 比较合适，二者使用需要权衡。

### **8.float 和 double 的区别是什么？**

- float 最多可以存储 8 位的十进制数，并在内存中占 4 字节。
- double 最可可以存储 16 位的十进制数，并在内存中占 8 字节。

### **9.MySQL 的内连接、左连接、右连接有什么区别？**

内连接关键字：inner join；左连接：left join；右连接：right join。

内连接是把匹配的关联数据显示出来；左连接是左边的表全部显示出来，右边的表显示出符合条件的数据；右连接正好相反。

### **10.MySQL 索引是怎么实现的？**

索引是满足某种特定查找算法的数据结构，而这些数据结构会以某种方式指向数据，从而实现高效查找数据。

具体来说 MySQL 中的索引，不同的数据引擎实现有所不同，但目前主流的数据库引擎的索引都是 B+ 树实现的，B+ 树的搜索效率，可以到达二分法的性能，找到数据区域之后就找到了完整的数据结构了，所有索引的性能也是更好的。

###  **11.怎么验证 MySQL 的索引是否满足需求？**

使用 explain 查看 SQL 是如何执行查询语句的，从而分析你的索引是否满足需求。

explain 语法：explain select * from table where type=1。

### 12.说一下数据库的事务隔离？

MySQL 的事务隔离是在 MySQL. ini 配置文件里添加的，在文件的最后添加：

```mysql
transaction-isolation = REPEATABLE-READ
```

可用的配置值：READ-UNCOMMITTED、READ-COMMITTED、REPEATABLE-READ、SERIALIZABLE。

**READ-UNCOMMITTED :**未提交读，最低隔离级别、事务未提交前，就可以被其他事务读取（会出现幻读、脏读、不可重复读）

**READ-COMMITTED**：提交读，一个事务提交后才能被其他事务读取到（会出现幻读、不可重复读）

**REPEATABLE-READ：**可重复读，MySQL默认级别，保证多次读取同一个数据时，其值和事务开始时的内容保持一致。禁止读取到别的事务未提交的数据（会造成幻读）

**SERIALIZABLE**序列化，代价最高最可靠的隔离级别，该隔离级别能防止脏读、不可重复读、幻读。

### **13脏读、不可重复读、幻读分别是什么？**

- **脏读：** 表示一个事务读取到另一个事务中还未提交的事务。比如  A尝试插入记录 X ,此时事务还未提交，然后另一个事务尝试读取到了 记录X 
- **不可重复读**：指在一个事务内，多次读同一数据
- **幻读**：指同一个事务内多次查询返回结果集不一样。 比如同一个事务 A  第一次查询时候有n 条记录，但是第二次查询的时候有n+1 条记录，这就好像产生了幻觉。发生幻读的原因也是另外一个事务新增、删除或者修改了第一个事务结果集里面的数据。同一个记录的数据被修改了，所有数据行的记录就变多或者变少了。

###  **14. 说一下 MySQL 常用的引擎？**

**InnoDB 引擎**：

- mysql 5.1 后默认的数据库引擎，提供了对数据库 acid 事务的支持，
- 并且还提供了行级锁和外键的约束，它的设计的目标就是**处理大数据容量的数据库系统**。
- MySQL 运行的时候，InnoDB 会在内存中建立缓冲池，用于缓冲数据和索引。
- 但是该引擎是不支持全文搜索，同时启动也比较的慢，它是不会保存表的行数的，所以当进行 select count(*) from table 指令的时候，需要**进行扫描全表**。
- 由于锁的粒度小，写操作是不会锁定全表的,所以在并发度较高的场景下使用会提升效率的。

**MyIASM 引擎**：

- **不提供事务的支持，也不支持行级锁和外键**。
- 因此当执行插入和更新语句时，即执行写操作的时候需要**锁定这个表**，所以会导致效率会降低。
- 不过和 InnoDB 不同的是，MyIASM 引擎是保存了表的行数，于是当进行 select count(*) from table 语句时，可以直接的读取已经保存的值而不需要进行扫描全表。
- 所以，如果表的**读操作远远多于写操作时**，并且不需要事务的支持的，可以将 MyIASM 作为数据库引擎的首选。

### **15.说一下 MySQL 的行锁和表锁？**

MyISAM 只支持表锁，InnoDB 支持表锁和行锁，默认为行锁。

- - 表级锁：开销小，加锁快，不会出现死锁。**锁定粒度大**，发生**锁冲突的概率最高**，并发量最低。
  - 行级锁：**开销大，加锁慢，会出现死锁**。锁力度小，发生锁冲突的概率小，并发度最高。

  ### **16. 说一下乐观锁和悲观锁？**

  **乐观锁：**

  - 每次去拿数据的时候都认为别人不会修改，所以不会上锁，
  - 但是在提交更新的时候会判断一下在此期间别人有没有去更新这个数据。

  **悲观锁**：

  - 每次去拿数据的时候都认为别人会修改，所以每次在拿数据的时候**都会上锁**，这样别人想拿这个数据就会阻止，直到这个锁被释放。

  **实现乐观锁**

  - 数据库的乐观锁需要自己实现，在表里面添加一个 version 字段，每次修改成功值加 1，这样每次修改的时候先对比一下，自己拥有的 version 和数据库现在的 version 是否一致，如果不一致就不修改，这样就实现了乐观锁。

### **17.mysql 问题排查都有哪些手段？**

- 使用 show processlist 命令查看当前所有连接信息。
- 使用 explain 命令查询 SQL 语句执行计划。
- 开启慢查询日志，查看慢查询的 SQL。

### **18. 如何做 mysql 的性能优化？**

- 为搜索字段创建索引。
- 避免使用 select *，列出需要查询的字段。
- 垂直分割分表。
- 选择正确的存储引擎。

### 19.MySQL基础

**创建数据库**

```mysql
create database 数据库名称;
show databases; //查看数据库
```

**修改数据库**

```
alter database 数据库名称 default character set 编码方式 collate 编码方式_bin;
eg: alter databse tb_user deffault character set utf8 collate utf8_bin;
```

 **删除数据库**

```
drop database 数据库名;
```

**数据类型**

![1603426546819](C:\Users\hp\Desktop\java面试题\img\1603426546819.jpg)

**创建数据表**

```
create table 表名
{
	字段 1， 数据类型，
	字段 2， 数据类型，
}
```

#### 数据表的基本操作

**查看数据表**

```
show create table 表名;
describe 表名; //或者简写desc 表名
```

**修改数据表**

```
alter table 旧表名 rename [TO] 新表名;
```

**修改字段名**

```
alter table  change 表名 旧字段名 新字段名 新数据类型
eg: alter table change tb_user name user_name varchar(20); 
```

**修改字段的数据类型**

```
alter table 表名 modify 字段名 数据类型;
eg:alter table tb_user modify user_name int(20);
```

**添加字段**

```
alter table 表名 add 新字段名 数据类型[约束条件][first|after 已存在字段名]
eg: alter table tb_user add age int(10);
```

**删除字段**

```
alter table 表名 drop 字段名;
```

**修改字段的排列位置**

```
alter table 表名 modify 字段名 1 数据类型 First|after 字段名 2 ；
```

**删除数据表**

```
drop table 表名；
```

#### 表的约束

**单字段主键**

```
字段名 数据类型 primary key
eg：create table tb_test(id int primary key,name varchar(20),grade float);
```

**多字段主键**

```
primary key(字段名 1,字段名 2，...字段名 n)
eg：
create table tb_test(
stu_id int,
course_id int,
name varchar(20),
grade float,
primary key(stu_id,course_id)
);
```

**唯一约束**

```
字段名 数据类型 unique；
eg:
create table tb_test1(
id int primary key,
stu_id int unique,
name varchar(20) not null
);
```

**默认约束**

```
字段名 数据类型 deffault 默认值;
eg:
create table tb_test2(
id int primary key auto_increment,
stu_id int unique,
grade float deffault 0
);
```

#### 索引

**普通索引**

- 普通索引是由KEY 或INDEX定义的索引，他是MySQL中的基本索引类型，可以创建在任何数据类型中，

**唯一性索引**

- 唯一性索引由 unique 定义的索引，该索引所在字段的值必须是唯一的。

**全文索引**

- 只有MyISAM存储引擎支持全文索引

**创建索引**

1. **创建表的时候创建索引**

   ```
   create table 表名 (字段名 数据类型[完整性约束条件],
   				  字段名 数据类型[完整性约束条件],
   				  ...
   字段名 数据类型[UNIQUE|FULLTEXT|SPATIAL] INDEX|KEY[别名] (字段名 1[长度]) [ASC|DESC] )
   
   eg:
   create table tb_index1(
   id int primary key auto_increment,
   stu_id int unique,
   grade float deffault 0,
   index iddd (id)
   );
   ```

2. **在已经存在的表上创造索引,使用 create index** 

   ```
   create [UNIQUE|FULLTEXT|SPATIAL] index 索引名 on 表名 (字段名 [(长度)] [ASC|DESC])
   
   eg:
   create unique index unique_idx on tb_index1(id);
   ```

3. **在已经存在的表上创造索引,使用 alter table **

   ```
   alter table 表名 add [UNIQUE|FULLTEXT|SPATIAL] index 索引名 (字段名 [(长度)] [ASC|DESC])
   ```

   

**删除索引**

1. 使用 alter table 删除索引

   ```
   alter table 表名 drop index 索引名
   
   eg:
   alter table tb_index1 drop index unique_idx;
   ```

   

2. 使用 drop index  删除索引

   ```
   drop index 索引名 on 表名 ;
   
   eg:
   drop index unique_idx on tb_index1;
   ```

   

#### CRUD

**1. 添加，指定字段名**

```
insert into 表名(字段名 1 ，字段名 2， ...) values(值 1，值 2，...);
```

**添加，不指定字段名**

```
insert into 表名 values(值 1，值 2，...);//添加顺序必须和表中定义的字段顺序一样
```

**其他用法**

```
insert into 表名 set 字段名 1 =值 1[，字段名 2 =值 2， ...]；
eg:
insert into tb_user set name ='李四', age= 18;
```

**添加多条记录**

```
insert into 表名[(字段名 1 ，字段名 2， ...)] values
(值 1，值 2，...),
...
(值 1，值 2，...),

eg:
insert into tb_user values
('王五',15),
('李四',19),
('赵二麻子',21);
```

**2. 更新数据**

```
update 表名 set 字段名 1= 值 1[,字段名 2=值 2, ...][where 条件表达式];//

update 表名 set 字段名 1= 值 1[,字段名 2=值 2, ...];//更新全部数据
```

**3. 删除数据**

```
delete from 表名 [where 条件表达式];
delete from 表名 ;// 删除所有数据
```

**truncate**

```
truncate [table] 表名；//删除所有数据

eg: truncate table tb_uesr;
```

delete 和truncate 区别？

- delete 后面可以跟where 子句，通过指定的条件表达式删除满足条件的部分记录，truncate 则删除全部记录。
- delete 删除表中数据后，再次像表中添加记录时，自动增长的值为删除时该字段的最大值 + 1；而 truncate 自动增加的默认初始值重新从1开始；

#### 单表查询

**select 语句**

```sql
select [distinct] * | {字段名 1，字段名 2, 字段名 3, ...}
	from 表名
	[where 条件表达式 1]
	[group by 字段名 [having 条件表达式 2]]
	[order by 字段名 [ASC | DESC]]
	[limit [offset] 记录数]
```

**IN 关键词**

```
select * | 字段名 1，字段名 2, 字段名 3, ...
	from 表名
	where 字段名 [NOT] IN (元素 1，元素 2，...)
	
eg:查询student 表中id 为 1，2，3的记录
select * from student where id in(1,2,3);
```

**between** **and**

```
select * | 字段名 1，字段名 2, 字段名 3, ...
	from 表名
	where 字段名 [NOT] between 值 1 and 值 2
区间： [a,b] 包括a 和b
```

**空值** :某些列可能为空值(NULL)，空值不同于0，也不同于空字符串

```
select * | 字段名 1，字段名 2, 字段名 3, ...
	from 表名
	where 字段名 IS [NOT] NULL
```

**distinct**：去重

```
select distinct gender from student;
```

**⭐当distinct 关键字作用于多个字段**

```sql
select dinstinct gender ,name from student;
```

**只有dinstinct 关键字后指定的多个字段值都相同，才会被认做是重复记录**

**like**

```
select * | 字段名 1，字段名 2, 字段名 3, ...
	from 表名
	where 字段名  [NOT] LIKE ‘匹配字符串'
```

**% :** 任意长度

**_ :** 单个字符

通配符："\%" ---> %

**AND**

```
select * | 字段名 1，字段名 2, 字段名 3, ...
	from 表名
	where 条件表达式 1 AND 条件表达式 2 [...条件表达式 n]
```

**OR**

```
select * | 字段名 1，字段名 2, 字段名 3, ...
	from 表名
	where 条件表达式 1 OR 条件表达式 2 [...条件表达式 n]
```

**OR 和AND 同时使用注意优先级**

AND 的优先级高于 OR

```
select * from student where gender ='女' OR gender ='男' AND grade =100;

```

先执行gender ='男' AND grade =100 满足这个条件或者  gender ='女' 这个条件则输出

#### 聚合查询

COUNT()

SUM()

AVG()

MAX()

MIN()

```
select count(*) from student;
```

**order** **by**:默认升序 ASC

```
select * | 字段名 1，字段名 2, 字段名 3, ...
	from 表名
	order by 字段名 1[ASC | DESC],  字段名 2[ASC | DESC]
	
eg:
select * from student order by grade;
```

**group** **by**：分组查询

```
select * | 字段名 1，字段名 2, 字段名 3, ...
	from 表名
	group by 字段名 1,字段名 2,... [having 条件表达式]

eg:
mysql> select *from student group by gender;
+----+--------+-------+--------+
| id | name   | grade | gender |
+----+--------+-------+--------+
| 10 | 雍正 |    33 | NULL   |
|  2 | 李四 |    45 | 女    |
|  1 | 王五 |    23 | 男    |
+----+--------+-------+--------+
3 rows in set (0.00 sec)
```

**group by 和聚合函数一起使用**

```
mysql> select count(*),gender from student group by gender;
+----------+--------+
| count(*) | gender |
+----------+--------+
|        1 | NULL   |
|        4 | 女    |
|        5 | 男    |
+----------+--------+
3 rows in set (0.00 sec)

```

**group 和having 一起使用**

```
mysql> select sum(grade),gender from student group by gender having sum(grade)<300;
+------------+--------+
| sum(grade) | gender |
+------------+--------+
|         33 | NULL   |
|        159 | 女    |
|         80 | 男    |
+------------+--------+
3 rows in set (0.00 sec)
```

**limit限制查询的数量**

```
select * | 字段名 1，字段名 2, 字段名 3, ...
	from 表名
	limit [offset,] 记录数
```

offset表示偏移量，默认为 0；

如果偏移量为0则从查询的第一条记录开始

偏移量为1 则表示从查询结果中的第二条记录开始，以此类推

**取别名**

```
select * from 表名 [AS] 别名；
```

#### 多表操作

**外键**：为了保证数据的完整性，将两表之间的数据建立关系。

**创建外键**

```
alter table 表名 add constraint FK_ID foreign key(外键字段名) references 外键表名 (主键字段名);
```

eg

```
mysql> alter table student add constraint FK_ID foreign key(gid) references grade (id);
Query OK, 0 rows affected (0.06 sec)
Records: 0  Duplicates: 0  Warnings: 0
```

**查看创建的外键**

```
mysql> show create table student;
| Table   | Create Table                                                   
| student | CREATE TABLE `student` (
  `sid` int(4) NOT NULL,
  `sname` varchar(36) DEFAULT NULL,
  `gid` int(4) NOT NULL,
  PRIMARY KEY (`sid`),
  KEY `FK_ID` (`gid`),
  CONSTRAINT `FK_ID` FOREIGN KEY (`gid`) REFERENCES `grade` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 
```

**注意：**

- 建立外建的表必须是InnoDB引擎，不能是临时表。MySQL中只有InnoDB支持外键
- 定义外键名时，不能加引号，如 constraint 'FK_ID' 或constraint ”FK_ID“都是错误的；

**删除外键约束**

```
alter table 表名 drop foreign key 外键名

eg：
mysql> alter table student drop foreign key FK_IDS;

```

**操作关联表**

添加数据

student

```
mysql> insert into student values(1,'周润发',1),
(2,'刘德华',1),(3,'周润发',2),(4,'刘德华',2);
```

grade

```
mysql> insert into grade values(1,'软件一班'),(2,'软件二班');
```

```
mysql> select * from grade;
+----+--------------+
| id | name         |
+----+--------------+
|  1 | 软件一班 |
|  2 | 软件二班 |
+----+--------------+
2 rows in set (0.00 sec)

mysql> select * from student;
+-----+-----------+-----+
| sid | sname     | gid |
+-----+-----------+-----+
|   1 | 周润发 |   1 |
|   2 | 刘德华 |   1 |
|   3 | 周润发 |   2 |
|   4 | 刘德华 |   2 |
+-----+-----------+-----+
4 rows in set (0.00 sec)

```

**注意：**

- 在上述语句中，添加的主键ID 为 1 ，2，由于student 表的外键和grade 表的主键关联，因此在为student 添加数据的时候，gid 只能是 1 或2

**删除数据**

- 具有关系的表中删除数据时，一定要先删除从表中的数据，然后在删除主表中的数据。

#### 连接查询

**内连接**：又称简单连接，或自然连接 ，内连接使用比较运算符对两个表中的数据进行比较，并列出与连接条件匹配的数据行，组合成新的记录，也就是说在内连接查询中，只有满足条件的记录才能出现在查询结果中

```
select 查询字段 from 表1 [INNER] join 表2 on 表1.关系字段=表2.关系字段

eg:
select employee.name,department.dname 
from deparment join employee on deparment.did=employee.did;

使用 where 语句
select employee.name,department.dname 
from deparment,employee
where deparment.did=employee.did; 
```

**自连接**

如果在一个连接查询中，涉及的两个表是同一个表，这种查询称为**自连接**查询。

自连接是一种特殊的内连接，它是指相互连接的表在物理上为同一个表，但逻辑上分为两个表，

例如要查询王红所在部门有哪些员工，就可以使用自连接查询。

```
select p1.* from employee p1 join employee p2 on p1.did=p2.did where p2.name='王红';
```

**外连接**

```
select 所查字段 
from 表 1 left|right [outer] join 表 2 on 表 1.关系字段=表 2.关系字段 
where 条件
```

#### 子查询

**子查询**：指一个查询语句嵌套在另一个查询语句内部的查询。在执行查询语句时，首先执行子查询中的语句，然后将返回的结果作为外层查询的过滤条件，在子查询中通常可以使用IN、exists、any、all操作符。

**带 IN 关键字的子查询**：使用IN关键字进行子查询时，内层查询语句仅返回一个数据列，这个数据列的值将供外层语句进行比较操作

查询存在年龄为20岁的员工的部门

```
select * from department where id in(select did from employee where age=20);
```

**带 EXIISTS 关键字的子查询**：EXISTS 关键字后面的参数可以是任意一个子查询，这个子查询的作用相当于测试，他不产生任何数据，只返回TRUE 或FALSE ，当返回值为TRUE 时，外层查询才会执行。

EXISTS 关键字比 IN 关键字的运行效率高

**带any 关键字的子查询**： any 关键字表示，满足其中任意一个条件，它允许创建一个表达式对子查询的返回值列表进行比较，只要满足内层子查询的任意一个比较条件，就返回一个结果作为外层查询条件

 ```
select * from department where did > any (select did from employee);
did dname
2   媒体部
3   研发部
5   人事部
首先子查询会将employee 表中的所有did 查寻出来，分别为1、 1、 2、 4，然后将department 表中did 的值与之进行比较，只要大于employee.did 中的任意一个值，就是符合条件的查询结果，由于department 表中的媒体部，研发部，人事部 的did都大于employee 表中的did（did=1），因此输出结果为媒体部、研发部、和人事部
 ```

**带 ALL 关键字的子查询**：ALL 关键字的子查询返回的结果需要同时满足所有的内层查询条件

```
select * from department where did > all(select did from employee);
```

**带比较运算符的子查询**  :rugby_football:如< >= 、= 、!=

```
select * from department where did = (select did from employee where name ='赵四');
```



### 20. delete 和truncate 区别？

- delete 后面可以跟where 子句，通过指定的条件表达式删除满足条件的部分记录，truncate 则删除全部记录。
- delete 删除表中数据后，再次像表中添加记录时，自动增长的值为删除时该字段的最大值 + 1；而 truncate 自动增加的默认初始值重新从1开始；

## ⭐Redis⭐

### **1.redis 是什么？都有哪些使用场景？**

Redis是一个开源的使用ANSI C语言编写、支持网络、可基于内存亦可持久化的日志型、Key-Value数据库，并提供多种语言的API。

Redis 使用场景：

- 数据高并发的读写
- 海量数据的读写
- 对扩展性要求高的数据

### **2.redis 有哪些功能？**

- 数据缓存功能
- 分布式锁的功能
- 支持数据持久化
- 支持事务
- 支持消息队列

### **3.redis 和 memecache 有什么区别？**

- memcached所有的值均是简单的字符串，redis作为其替代者，支持更为丰富的数据类型
- redis的速度比memcached快很多
- redis可以持久化其数据

### **4.redis 为什么是单线程的？**

因为 **cpu 不是 Redis 的瓶颈**，Redis 的瓶颈最有可能是**机器内存或者网络带宽**。既然单线程容易实现，而且 cpu 又不会成为瓶颈，那就顺理成章地采用单线程的方案了。

关于 Redis 的性能，官方网站也有，普通笔记本轻松处理每秒几十万的请求。

而且单线程并不代表就慢 nginx 和 nodejs 也都是高性能单线程的代表。

### **5.什么是缓存穿透？怎么解决？**

**缓存穿透：** 表示恶意用户频繁的的请求缓存不存在的数据，以致这些请求短时间内落在数据库上，导致数据库性能急剧下降，最终影响服务整体的性能。

**解决方案：**

1. 布隆过滤 :将所有可能存在的数据存到一个bitMap 中，不存在的数据就会进行拦截
2. 对查询结果为空的情况也进行缓存(不管数据是否存在，还是系统故障)，缓存时间设置短一点，不超过5分钟。
3. 使用互斥锁排队。当从缓存中获取数据失败时，给当前接口加上锁，从数据库中加载完数据并写入后再释放锁。若其它线程获取锁失败，则等待一段时间后重试。

### 6.什么是缓存雪崩？如何解决？

**缓存雪崩**：在短时间有大量缓存失效，如果这段时间有大量的请求发生同样会导致数据库发生宕机。

**解决办法：**

1. 建立备份缓存，缓存 A 和缓存 B，A 设置超时时间，B 不设值超时时间，先从 A 读缓存，A 没有读 B，并且更新 A 缓存和 B 缓存。
2. 计算数据缓存节点的时候采用一致性 hash 算法，这样在节点数量发生改变时不会存在大量的缓存数据需要迁移的情况发生。

- 事前 ：redis 高可用 主从+哨兵 redis cluster 避免全盘崩溃
- 事中：hystrix 限流降级，避免MySQL被打死
- 事后：redis 持久化，快速恢复缓存数据

### **7.redis 支持的数据类型有哪些？**

**string**

- 格式: set key value
- string类型是二进制安全的。意思是redis的string可以包含任何数据。比如jpg图片或者序列化的对象 。
- string类型是Redis最基本的数据类型，一个键最大能存储512MB。

**list**

- redis 列表是简单的字符串列表，按照插入顺序排序。你可以添加一个元素到列表的头部（左边）或者尾部（右边）

- 格式: lpush name value

  在 key 对应 list 的头部添加字符串元素

- 格式: rpush name value

  在 key 对应 list 的尾部添加字符串元素

- 格式: lrem name index

  key 对应 list 中删除 count 个和 value 相同的元素

- 格式: llen name 

  返回 key 对应 list 的长度

**hash**

- 格式: hmset name key1 value1 key2 value2
- Redis hash 是一个键值(key=>value)对集合。
- Redis hash是一个string类型的field和value的映射表，hash特别适合用于存储对象。

**set** 

- 格式: sadd name value
- Redis的Set是string类型的无序集合。
- 集合是通过哈希表实现的，所以添加，删除，查找的复杂度都是O(1)。

**zset**

- (sorted set：有序集合) 不允许重复

- 格式: zadd name score value
- 不同的是每个元素都会关联一个double类型的分数。redis正是通过分数来为集合中的成员进行从小到大的排序。
- zset的成员是唯一的,但分数(score)却可以重复。

### **8.怎么保证缓存和数据库数据的一致性？**

**将不一致分为三种情况：**

1.  数据库有数据，缓存没有数据；

2. 数据库有数据，缓存也有数据，数据不相等；

3. 数据库没有数据，缓存有数据。

**缓存模式** 

- 首先尝试从缓存读取，读到数据则直接返回；如果读不到，就读数据库，并将数据写到缓存，并返回
- 需要更新数据时，先更新数据库，然后把缓存里对应的数据失效掉（删除）

1. 对于第一种，在读数据的时候，会自动把数据库的数据写到缓存，因此不一致自动消除.
2. 对于第二种，数据最终变成了不相等，但他们之前在某一个时间点一定是相等的（不管你使用懒加载还是预加载的方式，在缓存加载的那一刻，它一定和数据库一致）。这种不一致，一定是由于你更新数据所引发的。前面我们讲了更新数据的策略，先更新数据库，然后删除缓存。因此，不一致的原因，一定是数据库更新了，但是删除缓存失败了。
3. 对于第三种，情况和第二种类似，你把数据库的数据删了，但是删除缓存的时候失败了。

**最终的结论是，需要解决的不一致，产生的原因是更新数据库成功，但是删除缓存失败。**

**解决方案**

1. 对删除缓存进行重试，数据的一致性要求越高，重试的越快
2. 定期全量更新，简单的说就是定期把缓存全部清掉，然后在全量加载
3. 给所有缓存一个失效期

**针对并发情况**

并发不高

- 读:redis ->没有，读 MySQL-> 把MySQL数据写回redis；有直接从redis中读取
- 写：写MySQL -> 成功 ，再写redis

并发高

- 读:redis ->没有，读 MySQL-> 把MySQL数据写回redis；有直接从redis中读取
- 写：异步 先写入redis 缓存，直接返回；定期将数据保存到MySQL中，可以做到多次更新，一次保存

### **9.redis 持久化有几种方式？**

Redis 的持久化有两种方式，或者说有两种策略：

- RDB（Redis Database）：指定的时间间隔能对你的数据进行快照存储。
- AOF（Append Only File）：每一个收到的写命令都通过write函数追加到文件中。

### **10.redis 怎么实现分布式锁？**

Redis 分布式锁其实就是在系统里面占一个“坑”，其他程序也要占“坑”的时候，占用成功了就可以继续执行，失败了就只能放弃或稍后重试。

占坑一般使用 setnx(set if not exists)指令，只允许被一个程序占有，使用完调用 del 释放锁。

### **11.redis 分布式锁有什么缺陷？**

Redis 分布式锁不能解决超时的问题，分布式锁有一个超时时间，程序的执行如果超出了锁的超时时间就会出现问题。

### **12.redis 如何做内存优化？**

尽可能使用散**列表（hashes）**，散列表（是说散列表里面存储的数少）使用的内存非常小，所以你应该尽可能的将你的数据模型抽象到一个散列表里面。

比如你的web系统中有一个用户对象，不要为这个用户的名称，姓氏，邮箱，密码设置单独的key,而是应该把这个用户的所有信息存储到一张散列表里面。

### **13.redis 淘汰策略有哪些？**

- volatile-lru：从已设置过期时间的数据集（server. db[i]. expires）中挑选最近最少使用的数据淘汰。
- volatile-ttl：从已设置过期时间的数据集（server. db[i]. expires）中挑选将要过期的数据淘汰。
- volatile-random：从已设置过期时间的数据集（server. db[i]. expires）中任意选择数据淘汰。
- allkeys-lru：从数据集（server. db[i]. dict）中挑选最近最少使用的数据淘汰。
- allkeys-random：从数据集（server. db[i]. dict）中任意选择数据淘汰。
- no-enviction（驱逐）：禁止驱逐数据。

### **14. redis 常见的性能问题有哪些？该如何解决？**

- 主服务器写内存快照，会阻塞主线程的工作，当快照比较大时对性能影响是非常大的，会间断性暂停服务，所以主服务器最好不要写内存快照。
- Redis 主从复制的性能问题，为了主从复制的速度和连接的稳定性，主从库最好在同一个局域网内。

## ⭐计算机网络⭐

### TCP和UDP的区别

**TCP** 

- 面向连接的传输控制协议，面向字节流
- 使用流量控制和拥塞控制的可靠传输
- 点到点的连接
- 对系统资源要求较多

**UDP**

- 无连接的不可靠传输
- 支持一对一，一对多，多对多，多对一的交互通信
- 具有较好的实时性
- 对系统资源要求较少

### **2.http 响应码 301 和 302 代表的是什么？有什么区别？**

答：301，302 都是HTTP状态的编码，都代表着某个URL发生了转移。

**区别：** 

- 301 redirect: 301 代表永久性转移(Permanently Moved)。
- 302 redirect: 302 代表暂时性转移(Temporarily Moved )。

### **3. forward 和 redirect 的区别？**

Forward和Redirect代表了两种请求转发方式：直接转发和间接转发。

**直接转发方式（Forward）**，客户端和浏览器只发出一次请求，Servlet、HTML、JSP或其它信息资源，由第二个信息资源响应该请求，在请求对象request中，保存的对象对于每个信息资源是共享的。

**间接转发方式（Redirect）**实际是**两次HTTP请求**，服务器端在响应第一次请求的时候，让浏览器再向另外一个URL发出请求，从而达到转发的目的。

**举个通俗的例子：**　

直接转发就相当于：“A找B借钱，B说没有，B去找C借，借到借不到都会把消息传递给A”；　　

间接转发就相当于："A找B借钱，B说没有，让A去找C借"。

### **4.tcp 为什么要三次握手，两次不行吗？为什么？**

为了实现可靠数据传输， TCP 协议的通信双方， **都必须维护一个序列号**， 以标识发送出去的数据包中， 哪些是已经被对方收到的。

 三次握手的过程即是通信双方相互告知序列号起始值， 并确认对方已经收到了序列号起始值的必经步骤。

如果只是两次握手， 至多只有连接发起方的起始序列号能被确认， 另一方选择的序列号则得不到确认。

### **5.说一下 tcp 粘包是怎么产生的？**

**①. 发送方产生粘包**

采用TCP协议传输数据的客户端与服务器经常是保持一个长连接的状态（一次连接发一次数据不存在粘包），双方在连接不断开的情况下，可以一直传输数据；但当发送的数据包过于的小时，那么TCP协议默认的会启用Nagle算法，将这些较小的数据包进行合并发送（缓冲区数据发送是一个堆压的过程）；这个合并过程就是在发送缓冲区中进行的，也就是说数据发送出来它已经是粘包的状态了。

![image-20201010084134035](C:\Users\hp\Desktop\java面试题\img\image-20201010084134035.png)

**②. 接收方产生粘包**

接收方采用TCP协议接收数据时的过程是这样的：数据到底接收方，从网络模型的下方传递至传输层，传输层的TCP协议处理是将其放置接收缓冲区，然后由应用层来主动获取（C语言用recv、read等函数）；这时会出现一个问题，就是我们在程序中调用的读取数据函数不能及时的把缓冲区中的数据拿出来，而下一个数据又到来并有一部分放入的缓冲区末尾，等我们读取数据时就是一个粘包。（放数据的速度 > 应用层拿数据速度） 

![image-20201010084159582](C:\Users\hp\Desktop\java面试题\img\image-20201010084159582.png)

### 6.计算机网络体系模型

![image-20201010084426656](C:\Users\hp\Desktop\java面试题\img\image-20201010084426656.png)

### 7.如何实现跨域

**方式一：JSONP跨域**

JSONP（JSON with Padding）是数据格式JSON的一种“使用模式”，可以让网页从别的网域要数据。根据 XmlHttpRequest 对象受到同源策略的影响，而利用 <script>元素的这个开放策略，网页可以得到从其他来源动态产生的JSON数据，而这种使用模式就是所谓的 JSONP。用JSONP抓到的数据并不是JSON，而是任意的JavaScript，用 JavaScript解释器运行而不是用JSON解析器解析。所有，通过Chrome查看所有JSONP发送的Get请求都是js类型，而非XHR。 

![image-20201010084733953](C:\Users\hp\Desktop\java面试题\img\image-20201010084733953.png)

**缺点：**

- 只能使用Get请求
- 不能注册success、error等事件监听函数，不能很容易的确定JSONP请求是否失败
- JSONP是从其他域中加载代码执行，容易受到跨站请求伪造的攻击，其安全性无法确保

**方式二：CORS**

Cross-Origin Resource Sharing（CORS）跨域资源共享是一份浏览器技术的规范，提供了 Web 服务从不同域传来沙盒脚本的方法，以避开浏览器的同源策略，确保安全的跨域数据传输。现代浏览器使用CORS在API容器如XMLHttpRequest来减少HTTP请求的风险来源。与 JSONP 不同，CORS 除了 GET 要求方法以外也支持其他的 HTTP 要求。服务器一般需要增加如下响应头的一种或几种：

```java
Access-Control-Allow-Origin: *
Access-Control-Allow-Methods: POST, GET, OPTIONS
Access-Control-Allow-Headers: X-PINGOTHER, Content-Type
Access-Control-Max-Age: 86400
```

跨域请求默认不会携带Cookie信息，如果需要携带，请配置下述参数：

```java
"Access-Control-Allow-Credentials": true
// Ajax设置
"withCredentials": true
```

**方式三：代理**

同源策略是针对浏览器端进行的限制，可以通过服务器端来解决该问题

DomainA客户端（浏览器） ==> DomainA服务器 ==> DomainB服务器 ==> DomainA客户端（浏览器）

### **8.说一下 JSONP 实现原理？**

jsonp 即 json+padding，动态创建script标签，利用script标签的src属性可以获取任何域下的js脚本，通过这个特性(也可以说漏洞)，服务器端不在返货json格式，而是返回一段调用某个函数的js代码，在src中进行了调用，这样实现了跨域。

# ⭐算法⭐

### 请用java写二叉树算法，实现添加数据形成二叉树功能，并以先序的方式打印出来. 

