import numpy as np



X_train = np.genfromtxt("bird_sounds_features_train.csv", delimiter = ",")
y_train = np.genfromtxt("bird_sounds_labels_train.csv", delimiter = ",", dtype = int)
X_test = np.genfromtxt("bird_sounds_features_test.csv", delimiter = ",")
y_test = np.genfromtxt("bird_sounds_labels_test.csv", delimiter = ",", dtype = int)
print(X_train.shape)
print(y_train.shape)
print(X_test.shape)
print(y_test.shape)



# STEP 3
# assuming that there are K classes
# should return a numpy array with shape (K,)
def estimate_prior_probabilities(y):
    # your implementation starts below
    N = len(y)  
    classes = [1, 2, 3]  
    class_priors = []
    
    for c in classes:
        is_class_c = (y == c)
        
        N_c = np.sum(is_class_c)  
        
        prior = N_c / N  
        
        class_priors.append(prior)
    
    class_priors= np.array(class_priors)
    # your implementation ends above

    return(class_priors)

class_priors = estimate_prior_probabilities(y_train)
print(class_priors)



# STEP 4
# assuming that there are K classes and D features
# should return two numpy arrays with shape (K, D)
def estimate_means_and_deviations(X, y):
    # your implementation starts below
    K = 3
    D = X.shape[1]
    
    means = np.zeros((K, D))
    deviations = np.zeros((K, D))
    
    for c in [1, 2, 3]:  
        X_c = X[y == c]  
        
        means[c - 1] = np.mean(X_c, axis=0)  
        deviations[c - 1] = np.std(X_c, axis=0)
    
    # your implementation ends above
    return(means, deviations)

means, deviations = estimate_means_and_deviations(X_train, y_train)
print(means)
print(deviations)



# STEP 5
# assuming that there are N data points and K classes
# should return a numpy array with shape (N, K)
def calculate_score_values(X, means, deviations, class_priors):
    # your implementation starts below
    N = X.shape[0]  
    K = len(class_priors)  
    
    score_values = np.zeros((N, K))
    
    for c in [1, 2, 3]:
        idx = c - 1
        
        mu = means[idx]              
        sigma = deviations[idx]      
        prior = class_priors[idx]    
        
        log_likelihood_per_feature = -0.5 * np.log(2 * np.pi) - np.log(sigma) - ((X - mu) ** 2) / (2 * (sigma ** 2))
        
        score_values[:, idx] = np.sum(log_likelihood_per_feature, axis=1) + np.log(prior)
    # your implementation ends above
    return(score_values)

scores_train = calculate_score_values(X_train, means, deviations, class_priors)
print(scores_train[0:10, :])

scores_test = calculate_score_values(X_test, means, deviations, class_priors)
print(scores_test[0:10, :])



# STEP 6
# assuming that there are K classes
# should return a numpy array with shape (K, K)
def calculate_confusion_matrix(y_truth, scores):
    # your implementation starts below
    best_class_indices = np.argmax(scores, axis=1)
    
    y_pred = best_class_indices + 1
    
    classes = [1, 2, 3]
    K = len(classes)
    
    confusion_matrix = np.zeros((K, K), dtype=int)
    
    for r in classes:
        for c in classes:
            actual_is_r = (y_truth == r)
            
            predicted_is_c = (y_pred == c)
            
            both_match = actual_is_r & predicted_is_c
            
            count = np.sum(both_match)
            
            row_idx = r - 1
            col_idx = c - 1
            confusion_matrix[row_idx, col_idx] = count
    # your implementation ends above
    return(confusion_matrix)

confusion_train = calculate_confusion_matrix(y_train, scores_train)
print(confusion_train)
print("Training accuracy is {:.2f}%.".format(100 * np.sum(np.diag(confusion_train)) / np.sum(confusion_train)))

confusion_test = calculate_confusion_matrix(y_test, scores_test)
print(confusion_test)
print("Test accuracy is {:.2f}%.".format(100 * np.sum(np.diag(confusion_test)) / np.sum(confusion_test)))
