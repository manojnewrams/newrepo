provider "google" {
  project = "gke-tf-gitlab"
  region = "us-central-1"
  zone = "us-central1-a"
}

terraform {
    backend "gcs" {
    }
}

module "GCP_ROLES_CUSTOMS" {
source = "git::https://gitlab.mynisum.com/gitlab-templates/ci-templates/terraform/modules/gcp/tf-google-roles.git?ref=master"
role_id = "k8sOwner"
project = "gke-tf-gitlab"
role_title = "role-k8s-jv-custom"
role_stage = "BETA"
role_description = "This role create resources and has access to admin k8s"
role_permissions = [
  "container.clusters.create",
  "container.clusters.update",
  "storage.buckets.update",
  "storage.objects.create"
]
}

module "GCP_SERVICESACCOUNT" {
  source = "git::https://gitlab.mynisum.com/gitlab-templates/ci-templates/terraform/modules/gcp/tf-google-account-service.git?ref=master"
  name = "gke-tf-gitlab"
  display_name = "admin-k8s-jv"
  role = ["roles/container.admin","roles/compute.loadBalancerAdmin"]
  count-sa = 2
}

module "GCP_STORAGE" {
  source = "git::https://gitlab.mynisum.com/gitlab-templates/ci-templates/terraform/modules/gcp/tf-google-storage.git?ref=master"
  name = "tf-backends-gke"
  location = "us-central1"
  project = "gke-tf-gitlab"
  storage_class = "STANDARD"
  type = "SetStorageClass"
  action_storage_class = "REGIONAL"
  with_state = "LIVE" 
}

module "GCP_GKE" {
  source = "git::https://gitlab.mynisum.com/gitlab-templates/ci-templates/terraform/modules/gcp/tf-google-gke.git?ref=master"
  name = "k8s-test"
  location = "us-central1"
  name_pool = "awards"
  machine_type = "n1-standard-1"
}

