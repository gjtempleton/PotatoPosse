from django.db import models

# Create your models here.
class Disease(models.Model):
	LEAF = 'LD'
	PESTS = 'Pe'
	TUBER = 'TD'
	CATEGORY_CHOICES=((LEAF, 'Leaf Diseases'),(PESTS, 'Pests'),(TUBER, 'Tuber Diseases'))

	name = models.CharField(max_length=50)
	category = models.CharField(max_length=50, choices=CATEGORY_CHOICES)
	picture = models.FileField(upload_to='temp/%Y/%m/%d')
	description = models.CharField(max_length=500)
	test = models.CharField(max_length=50)

	def __str__(self):
		return self.name