
干净架构 - Clean Architecture

### Entities
封装业务规则。 这一层的组成可以是包含方法的对象，也可以是数据结构和方法
这一层封装了最通用和高层级的业务规则。 和业务规则不相关的改变不应该影响到这一层

### UseCases
这一层封装应用级别的业务规则，这一层实现了系统的use cases
这一层主要是实现对Entities层的编排，通过调用更底层的Entities层的对象来实现一个用例

### Interface Adapters
主要用来做UseCases/Entities层 和 外部系统（展示层，数据库层）之间的数据格式的适配

### Frameworks and Drivers
这一层是最外面的框架层，比如数据库/web 框架等
